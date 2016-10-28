%% HW3 for MATH531
% @Author: Baihan Lin
% @Date: Oct 2016

clear all; close all; clc;

%% Question 1

% part b)

% define rand seed and run time
rng(1);
Tmax = 1000;

% define variable
n = 0; % initial value
T(1) = 0; % start at time 0
X(1) = n; % at time 0, number of x molecule = n

% define concentration parameters
c = 100; % as an example
a = 1*c;
b = 1.5*c;

for t = 1:Tmax
    
    % define model parameters
    V = 100;
    alpha = 3*c^(-2)*t^(-1);
    beta = 0.6*c^(-2)*t^(-1);
    lamda = 2.95*t^(-1);
    delta = 0.25*t^(-1);
    
    % define CME parameters
    B = lamda*b + alpha*a*n*(n-1); % birth rate
    D = delta*n + beta*n*(n-1)*(n-2); % death rate
    
    % calculate Monte Carlo step
    time_B = -log(rand)./B; % waiting time for 1 birth
    time_D = -log(rand)./D; % waiting time for 1 death
    time = min(time_B, time_D); % Inter-step times
    
    if real(time_B) < real(time_D)
        n = n + 1;
    elseif real(time_B) > real(time_D)
        n = n - 1;
    end
    
    X(t) = n;
    T(t) = time;
    
end

figure();
stairs(T,X);
xlabel('time (s)');
ylabel('Number of X molecules');
