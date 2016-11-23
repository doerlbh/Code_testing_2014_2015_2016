%% HW4 for MATH381
% @Author: Baihan Lin
% @Date: Nov 2016

clear all; close all; clc;

%% Quesiton a: probability when needle length = 1a; and side of square = 2a


rng(1);

runs = 20;

figure

for run = 1:runs
    L = 1; % length of needle
    N = 100000; % trials to run

    n = 0; % number of crossed needles
    p = zeros(1, N); % probability of crossing

    r = rand(3,N); % random number for all steps

    for k = 1:N
        x1 = 2*L*r(1,k);
        y1 = 2*L*r(2,k);
        ang = 2*pi*r(3,k);
        x2 = x1 + L*cos(ang);
        y2 = y1 + L*sin(ang);

        y1diff = y1 - (2*L-2*x1); % y1diff < 0 if left zone, > 0 right zone
        y2diff = y2 - (2*L-2*x2); % y2diff < 0 if left zone, > 0 right zone

        if x2 < 0 || y2 < 0 || x2 > 2*L || y2 > 2*L || y1diff*y2diff < 0
            n = n+1;
        end

        p(k) = n/k;
    end

    plot(p); hold on
    title('probablity of crossing along trials in a square of 2a X 2a')
    xlabel('number of trails(iterations)'), ylabel('probability');

end

% From the figure, we find that experimentally, the estimated probability
% is about ~0.72.


%% Quesiton b: probability based on different needle length in a when side of square = 2a

rng(1);

runs = 20;
thrshd = 0.00001;

L = 1; % half length of the square side
targetP = 0.5;

N = 100000; % trials to run

arange = linspace(0,2);
p_a = zeros(100, N); % probability of crossing

r = rand(3,N); % random number for all steps

for acount = 1:100
    a = arange(acount);
    n = 0; % number of crossed needles
    for k = 1:N
        x1 = 2*L*r(1,k);
        y1 = 2*L*r(2,k);
        ang = 2*pi*r(3,k);
        x2 = x1 + a*cos(ang);
        y2 = y1 + a*sin(ang);

        y1diff = y1 - (2*L-2*x1); % y1diff < 0 if left zone, > 0 right zone
        y2diff = y2 - (2*L-2*x2); % y2diff < 0 if left zone, > 0 right zone

        if x2 < 0 || y2 < 0 || x2 > 2*L || y2 > 2*L || y1diff*y2diff < 0
            n = n+1;
        end

        p_a(acount,k) = n/k;
    end
end

figure
plot(arange, p_a(:,N)); hold on
plot(arange, 0.5*ones(1,100), '.r');
plot(0.6*ones(1,100), linspace(0,1), '.r');
plot(0.6,0.5,'o');
title('probablity of crossing in a square of 2a X 2a vs. L_needle in a');
xlabel('needle length in unit of a'), ylabel('probability');

%% Confirm needle length = 0.6a when side of square = 2a

rng(1);

runs = 20;

figure

for run = 1:runs
    a = 0.6;
    L = 1; % length of needle
    N = 100000; % trials to run

    n = 0; % number of crossed needles
    p = zeros(1, N); % probability of crossing

    r = rand(3,N); % random number for all steps

    for k = 1:N
        x1 = 2*L*r(1,k);
        y1 = 2*L*r(2,k);
        ang = 2*pi*r(3,k);
        x2 = x1 + a*cos(ang);
        y2 = y1 + a*sin(ang);

        y1diff = y1 - (2*L-2*x1); % y1diff < 0 if left zone, > 0 right zone
        y2diff = y2 - (2*L-2*x2); % y2diff < 0 if left zone, > 0 right zone

        if x2 < 0 || y2 < 0 || x2 > 2*L || y2 > 2*L || y1diff*y2diff < 0
            n = n+1;
        end

        p(k) = n/k;
    end

    plot(p); hold on
    title('probablity of crossing along trials in a square of 2a X 2a: L_needle = 0.6a')
    xlabel('number of trails(iterations)'), ylabel('probability');

end


%% Machine Learning

% rng(1);
% 
% runs = 20;
% thrshd = 0.001;
% 
% figure
% L = 1; % starting length of needle
% 
% resort = 1000;
% 
% targetP = 0.5;
% targetL = L;
% 
% for run = 1:runs
%     a = L;
%     N = 1000000; % trials to run
%     
%     n = 0; % number of crossed needles
%     nold = 0;
%     p = zeros(1, N); % probability of crossing
%     
%     r = rand(3,N); % random number for all steps
%     
%     for k = 1:N
%         x1 = 2*L*r(1,k);
%         y1 = 2*L*r(2,k);
%         ang = 2*pi*r(3,k);
%         x2 = x1 + a*cos(ang);
%         y2 = y1 + a*sin(ang);
%         
%         y1diff = y1 - (2*L-2*x1); % y1diff < 0 if left zone, > 0 right zone
%         y2diff = y2 - (2*L-2*x2); % y2diff < 0 if left zone, > 0 right zone
%         
%         if x2 < 0 || y2 < 0 || x2 > 2*L || y2 > 2*L || y1diff*y2diff < 0
%             n = n+1;
%             if k < resort
%                 nold = nold+1;
%             end
%         end
%         
%         if k < resort
%             p(k) = n/k;
%         else
%             p(k) = (n-nold)/(k-resort);
%         end
%         
%         if p(k) < targetP+thrshd && p(k) > targetP-thrshd
%             targetL = a;
%         else if p(k) < targetP
%                 if a < 2*L-0.01
%                     a = a + 0.01;
%                 end
%             else if p(k) > targetP
%                     if a > 0.01
%                         a = a - 0.01;
%                     end
%                 end
%             end
%         end
%         
%     end
%     
%     plot(p); hold on
%     title('training probablity of crossing towards targetP. TargetL = 0.4a');
%     xlabel('number of trails(iterations)'), ylabel('probability');
%     
% end
