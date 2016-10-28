%% HW3 for MATH531
% @Author: Baihan Lin
% @Date: Oct 2016

clear all; close all; clc;

%% Question 1

% define rand seed and run time
rng(1);
Tmax = 200000;
alltime1 = 0;
alltime2 = 0;

% define variable
n1 = 0; % initial value
T1(1) = 0; % start at time 0
W1(1) = 0; % waiting time at time 0 is 0
X1(1) = n1; % at time 0, number of x molecule = n
n2 = 0;
T2(1) = 0;
W2(1) = 0;
X2(1) = n2;

% define concentration parameters
c = 10; % as an example
a = 1*c;
b1 = 1.5*c;
b2 = 59*c;

for t = 1:Tmax
    
    % define model parameters
    V = 100;
    alpha = 3*c^(-2)*t^(-1);
    beta = 0.6*c^(-2)*t^(-1);
    lamda = 2.95*t^(-1);
    delta = 0.25*t^(-1);
    
    % define CME parameters
    B1 = lamda*b1*V + alpha*a*n1*(n1-1)/V; % birth rate
    D1 = delta*n1 + beta*n1*(n1-1)*(n1-2)/V^2; % death rate
    
    % calculate Monte Carlo step
    time_B1 = -log(rand)./B1; % waiting time for 1 birth
    time_D1 = -log(rand)./D1; % waiting time for 1 death
    time1 = min(time_B1, time_D1); % Inter-step times
    
    if real(time_B1) < real(time_D1)
        n1 = n1 + 1;
    elseif real(time_B1) > real(time_D1)
        n1 = n1 - 1;
    end
    
    alltime1 = alltime1 + time1;
    X1(t) = n1;
    T1(t) = alltime1;
    W1(t) = time1;
    
    % part c
    B2 = lamda*b2*V + alpha*a*n2*(n2-1)/V; 
    D2 = delta*n2 + beta*n2*(n2-1)*(n2-2)/V^2; 
    time_B2 = -log(rand)./B2; 
    time_D2 = -log(rand)./D2; 
    time2 = min(time_B2, time_D2);
   
    if real(time_B2) < real(time_D2)
        n2 = n2 + 1;
    elseif real(time_B2) > real(time_D2)
        n2 = n2 - 1;
    end
   
    alltime2 = alltime2 + time2;
    X2(t) = n2;
    T2(t) = alltime2;
    W2(t) = time2;
    
end

% part b)

fig1 = figure;
set(gca,'FontSize',20);
stairs(T1,X1);
xlabel('time (s)');
ylabel('Number of X molecules');
title('X(t) over time for V= 100, b = 1.5c, c = 10');

fig2 = figure;
set(gca,'FontSize',20);
hist(X1,100);
xlabel('Number of X molecules');
ylabel('Frequency');
title('X(t) Distribution for V= 100, b = 1.5c, c = 10');

% part c)

fig3 = figure;
set(gca,'FontSize',20);
stairs(T2,X2);
xlabel('time (s)');
ylabel('Number of X molecules');
title('X(t) over time for V= 100, b = 59c, c = 10');

fig4 = figure;
set(gca,'FontSize',20);
hist(X2,100);
xlabel('Number of X molecules');
ylabel('Frequency');
title('X(t) Distribution for V= 100, b = 59c, c = 10');

%% Question 3

% define rand seed and run time
rng(1);
Tmax = 5000000;
alltime = 0;

% define variable
n = 0; % initial value
T(1) = 0; % start at time 0
W(1) = 0; % waiting time at time 0 is 0
X(1) = n; % at time 0, number of x molecule = n

% define concentration parameters
c = 10; % as an example
a = 1*c;
b = 1.5*c;

for t = 1:Tmax
    
    % define model parameters
    V = 10000;
    alpha = 3*c^(-2)*t^(-1);
    beta = 0.6*c^(-2)*t^(-1);
    lamda = 2.95*t^(-1);
    delta = 0.25*t^(-1);
    
    % define CME parameters
    B = lamda*b*V + alpha*a*n*(n-1)/V; % birth rate
    D = delta*n + beta*n*(n-1)*(n-2)/V^2; % death rate
    
    % calculate Monte Carlo step
    time_B = -log(rand)./B; % waiting time for 1 birth
    time_D = -log(rand)./D; % waiting time for 1 death
    time = min(time_B, time_D); % Inter-step times
    
    if real(time_B) < real(time_D)
        n = n + 1;
    elseif real(time_B) > real(time_D)
        n = n - 1;
    end
    
    alltime = alltime + time;
    X(t) = n;
    T(t) = alltime;
    W(t) = time;
    
end

fig5 = figure;
set(gca,'FontSize',20);
stairs(T,X);
xlabel('time (s)');
ylabel('Concentration of X molecules');
title('nX(t)/V over time for V = 10000, b = 1.5c, c = 10');

fig6 = figure;
set(gca,'FontSize',20);
hist(X,100);
xlabel('Number of X molecules');
ylabel('Frequency');
title('nX(t)/V  Distribution for  V = 10000, b = 1.5c, c = 10');

