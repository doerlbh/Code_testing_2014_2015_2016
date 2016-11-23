%% HW4 for MATH381
% @Author: Baihan Lin
% @Date: Nov 2016

clear all; close all; clc;

%% Quesiton a

rng(1);

runs = 20;

figure
    
for run = 1:runs
    L = 2; % length of needle
    N = 100000; % trials to run
    
    n = 0; % number of crossed needles
    p = zeros(1, N); % probability of crossing
    
    r = rand(3,N); % random number for all steps
    
    for k = 1:N
        x1 = 2*L*r(1,k);
        y1 = 2*L*r(2,k);
        ang = 2*pi*r(3,k);
        x2 = x1 + 2*L*cos(ang);
        y2 = y1 + 2*L*sin(ang);
        
        y1diff = y1 - (2*L-2*x1); % y1diff < 0 if left zone, > 0 right zone
        y2diff = y2 - (2*L-2*x2); % y2diff < 0 if left zone, > 0 right zone
        
        if x2 < 0 || y2 < 0 || x2 > 2*L || y2 > 2*L || y1diff*y2diff < 0
            n = n+1;
        end
        
        p(k) = n/k;
    end
    
    plot(p); hold on
    title('probablity of crossing along trials')
    xlabel('number of trails(iterations)'), ylabel('probability');
    
end

% From the figure, we find that experimentally, the estimated probability
% is about 0.985... 


%% Quesiton b



