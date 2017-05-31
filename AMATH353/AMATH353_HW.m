%% HW for AMATH353
% @Author: Baihan Lin
% @Date: Apr 2017

clear all; close all; clc;

%% Different ways of visualizing a function of two variables
% Consider the function u(x,t) = exp(-(x-t)^2) + exp(-(x+t)^2)

%% Plot at discrete time shots
% Visualize the function at different time-shots as just a function of x

% Discretize the 'space' variable
x = linspace(-4,4,201);

% Choose time shots
time = [0,1,2];

% Loop over the number of time shots and create a figure for the
% corresponding value of time
figure(1)
for j = 1:length(time)
    u = exp(-(x-time(j)).^2) + exp(-(x+time(j)).^2);
    subplot(3,1,j)
    plot(x,u,'k-','LineWidth',1.5)
    hold all
    plot([x(1),x(end)],[0,0],'k--')
    plot([0,0],[-.1,1.1],'k--')
    title(sprintf('t = %d',time(j)))
    ylim([-.1,2.1])
    axis on
end


%% Slice Plot
% Plots of u at different time shots stacked next to each other
% Discretize both space and time
x = linspace(-4,4,201);
t = linspace(0,2,11);

% Create a grid, and define the z component as the function u
[X,T] = meshgrid(x,t);
Z = exp(-(X-T).^2) + exp(-(X+T).^2);

figure(2)
clf
waterfall(X,T,Z)
colormap default
xlabel('x','FontSize',15)
ylabel('t','FontSize',15)


%% Surface Plot
% Create a 3D figure with x as x-axis, t as y-axis and u as z-axis.

% Discretize both space and time
x = linspace(-4,4,201);
t = linspace(0,2,101);

% Create a grid, and define the z component as the function u
[X,T] = meshgrid(x,t);
Z = exp(-(X-T).^2) + exp(-(X+T).^2);

figure(3)
clf
surf(X,T,Z)
colormap default
xlabel('x','FontSize',15)
ylabel('t','FontSize',15)


%% Density Plot or xt-plane plots
% Discretize both space and time
x = linspace(-4,4,201);
t = linspace(0,2,101);

% Create a grid, and define the z component as the function u
[X,T] = meshgrid(x,t);
Z = exp(-(X-T).^2) + exp(-(X+T).^2);

figure(4)
clf
pcolor(X,T,Z)
colormap default
xlabel('x','FontSize',15)
ylabel('t','FontSize',15)


% %% Animation
% % Plot the function at each time-step in consecutive frames to create a
% % movie showing how the function evolves in time
%
% % Discretize both space and time
% x = linspace(-4,4,201);
% t = linspace(0,2,101);
%
% % Create a grid, and define the z component as the function u
% [X,T] = meshgrid(x,t);
% Z = exp(-(X-T).^2);
%
% figure(5)
% M = moviein(length(t));
% for j = 1:length(t)
%     plot(x,Z(j,:))
%     M(:,j) = getframe;
% end
% movie(M)

%% HW4 P3a

c=2;
n=20;
x=linspace(0,n);

t=0;
ct=c*t; %0
y=0.5*(exp(-(x-5+ct).^2)+exp(-(x-5-ct).^2))
plot(x,y);
title('Problem 3a: t = 0');
saveas(gcf, '/Users/DoerLBH/Dropbox/git/Code_testing/AMATH353/p3a0','png');

t=1;
ct=c*t; %2
x1=linspace(0,ct);
x2=linspace(ct,n);
y1=0.5*(exp(-(x1-5+ct).^2)-exp(-(-x1-5+ct).^2))
y2=0.5*(exp(-(x2-5+ct).^2)+exp(-(x2-5-ct).^2))
plot([x1 x2],[y1 y2]);
title('Problem 3a: t = 1');
saveas(gcf, '/Users/DoerLBH/Dropbox/git/Code_testing/AMATH353/p3a1','png');

t=2;
ct=c*t; %4
x1=linspace(0,ct);
x2=linspace(ct,n);
y1=0.5*(exp(-(x1-5+ct).^2)-exp(-(-x1-5+ct).^2))
y2=0.5*(exp(-(x2-5+ct).^2)+exp(-(x2-5-ct).^2))
plot([x1 x2],[y1 y2]);
title('Problem 3a: t = 2')
saveas(gcf, '/Users/DoerLBH/Dropbox/git/Code_testing/AMATH353/p3a2','png');

t=3;
ct=c*t; %6
x1=linspace(0,ct);
x2=linspace(ct,n);
y1=0.5*(exp(-(x1-5+ct).^2)-exp(-(-x1-5+ct).^2))
y2=0.5*(exp(-(x2-5+ct).^2)+exp(-(x2-5-ct).^2))
plot([x1 x2],[y1 y2]);title('Problem 3a: t = 3')
saveas(gcf, '/Users/DoerLBH/Dropbox/git/Code_testing/AMATH353/p3a3','png');

%% HW4 P3b

c=2;
n=20;
x=linspace(0,n);

t=0;
ct=c*t; %0
y=0.5*(exp(-(x-5+ct).^2)+exp(-(x-5-ct).^2))
plot(x,y);
title('Problem 3b: t = 0');
saveas(gcf, '/Users/DoerLBH/Dropbox/git/Code_testing/AMATH353/p3b0','png');

t=1;
ct=c*t; %2
x1=linspace(0,ct);
x2=linspace(ct,n);
y1=0.5*(exp(-(x1-5+ct).^2)+exp(-(-x1-5+ct).^2))
y2=0.5*(exp(-(x2-5+ct).^2)+exp(-(x2-5-ct).^2))
plot([x1 x2],[y1 y2]);
title('Problem 3b: t = 1');
saveas(gcf, '/Users/DoerLBH/Dropbox/git/Code_testing/AMATH353/p3b1','png');

t=2;
ct=c*t; %4
x1=linspace(0,ct);
x2=linspace(ct,n);
y1=0.5*(exp(-(x1-5+ct).^2)+exp(-(-x1-5+ct).^2))
y2=0.5*(exp(-(x2-5+ct).^2)+exp(-(x2-5-ct).^2))
plot([x1 x2],[y1 y2]);
title('Problem 3b: t = 2')
saveas(gcf, '/Users/DoerLBH/Dropbox/git/Code_testing/AMATH353/p3b2','png');

t=3;
ct=c*t; %6
x1=linspace(0,ct);
x2=linspace(ct,n);
y1=0.5*(exp(-(x1-5+ct).^2)+exp(-(-x1-5+ct).^2))
y2=0.5*(exp(-(x2-5+ct).^2)+exp(-(x2-5-ct).^2))
plot([x1 x2],[y1 y2]);title('Problem 3b: t = 3')
saveas(gcf, '/Users/DoerLBH/Dropbox/git/Code_testing/AMATH353/p3b3','png');

%% HW 5

x = linspace(0, 2);
y = x;
y(51:100)=2-x(51:100);
plot(x, y)
xlabel('x')
ylabel('y')

%%

x = linspace(-2, 2, 200);
y = x;
y(151:200)=2-x(151:200);
y(1:100)= y(101:200);
y(1:100) = - y(1:100);
plot(x, y)
xlabel('x')
ylabel('y')

%%

% Plotting Fourier partial sums for the function $f(x) = x^2, x in [-1,1]

close all
x = linspace(-1,1,101)';
y = x; % The true function

S = zeros(101,11);
% S(j) will be the (j-1)-th partial sum

S(:,1) = 0; % Constant term
for j = 2:11
    % Recall that x^2 has a Fourier cosine series since it is an even
    % function. Further, a_n = 4*(-1)^n /(n*pi)^2
    S(:,j) = S(:,j-1) + 2*(1-cos(pi*(j-1)))*sin((j-1)*pi*x)/((j-1)*pi);
end

figure(1)
clf
hold on
plot(x,y,'k','LineWidth',1.5)
plot(x,S(:,2),'ro-','LineWidth',1)
plot(x,S(:,3),'b+-','LineWidth',1)
plot(x,S(:,6),'g*-','LineWidth',1)
plot(x,S(:,11),'c.-','LineWidth',1)
legend('f(x)','S_1','S_2','S_5','S_{10}')
hold off

%%

% Plotting Fourier partial sums for the function $f(x) = x^2, x in [-1,1]

close all
x = linspace(0,1,101)';
y = 1-x.^2; % The true function

S = zeros(101,11);
% S(j) will be the (j-1)-th partial sum

S(:,1) = 0; % Constant term
for j = 2:11
    % Recall that x^2 has a Fourier cosine series since it is an even
    % function. Further, a_n = 4*(-1)^n /(n*pi)^2
    C=pi^2*(j-1)^2-2*pi*(j-1)*sin(pi*(j-1))-2*cos(pi*(j-1))+2;
    S(:,j) = S(:,j-1) + 2*C*sin((j-1)*pi*x)/((j-1)^3*pi^3);
end

figure(1)
clf
hold on
plot(x,y,'k','LineWidth',1.5)
plot(x,S(:,2),'ro-','LineWidth',1)
plot(x,S(:,3),'b+-','LineWidth',1)
plot(x,S(:,6),'g*-','LineWidth',1)
plot(x,S(:,11),'c.-','LineWidth',1)
legend('f(x)','S_1','S_2','S_5','S_{10}')
hold off

%%

xt=0:0.1:10;
u=zeros(101,4);
for t = 0:3
    for x = 0:100
        xr = x/10;
        if xr >2*t
            u(x+1,t+1)=0;
        else
            u(x+1,t+1)=(t-xr/2)/(1+(t-xr/2).^2);
        end
    end
end
figure(1)
clf
hold on
plot(xt,u(:,1),'ro-','LineWidth',1)
plot(xt,u(:,2),'b+-','LineWidth',1)
plot(xt,u(:,3),'g*-','LineWidth',1)
plot(xt,u(:,4),'c.-','LineWidth',1)
legend('t=0','t=1','t=2','t=3')
xlabel('x')
ylabel('u')

hold off

%%

t=0:0.01:2;

figure(1)
clf
hold on

for x0=-5:5;
    x = x0*exp(t.^2/2);
    plot(x,t,'c.-','LineWidth',1)
end
xlabel('x')
ylabel('t')

hold off

%%

t=0:0.01:1;

figure(1)
clf
hold on

for x0=-1:0.1:2;
    x = x0+t/(1+x0^2)^2;
    plot(x,t,'b.-','LineWidth',1)
end
plot([-1 linspace(-1,2.5)],0.9*ones(101,1), 'r--','LineWidth',1)
xlabel('x')
ylabel('t')

hold off

%% HW 8

t=0:0.01:1;

figure(1)
clf
hold on

for x0=-2:0.1:2;
    if x0 <= 0
        x = x0+4*t;
    else
        x = x0+1*t;
    end
    plot(x,t,'b.-','LineWidth',1)
end
% plot([-1 linspace(-1,2.5)],0.9*ones(101,1), 'r--','LineWidth',1)
xlabel('x')
ylabel('t')

hold off

%%

t=0:0.01:1;

figure(1)
clf
hold on

for x0=-1:0.1:2
    if x0 <= 0
        x = x0+t;
    else if x0 < 1
            x = x0+t*(1-x0);
        else
            x = x0;
        end
    end
    plot(x,t,'b.-','LineWidth',1)
end
% plot([-1 linspace(-1,2.5)],0.9*ones(101,1), 'r--','LineWidth',1)
xlabel('x')
ylabel('t')

hold off