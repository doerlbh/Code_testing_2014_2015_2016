%% HW for AMATH422
% @Author: Baihan Lin
% @Date: Oct 2016 / Mar 2017 / Apr 2017

clear all; close all; clc;


%% HW1

%% Question 1

initiate projection matrix A.
A1 = [0 1 5 0.5;
    0.5 0 0 0;
    0 0.9 0 0;
    0 0 0 0.95];

simulation of population dynamics
n_zero=[100; 100; 100; 100];
f_zero=[0.25;0.25;0.25;0.25];
Tmax=50;
n_vs_t=zeros(4,Tmax);
n_vs_t(:,1)=n_zero ;
f_vs_t=zeros(4,Tmax);
f_vs_t(:,1)=f_zero ;

for t=2:Tmax;
    n_vs_t(:,t)=A1*n_vs_t(:,t-1);
    n_sum = sum(n_vs_t(:,t));
    f_vs_t(:,t) = n_vs_t(:,t)/n_sum;
end

N = sum(n_vs_t);

fig1 = figure;
set(gca,'FontSize',20);
plot(1:Tmax,log(N'),'.-','MarkerSize',14);
xlabel('t','FontSize',20);
ylabel('log(N)','FontSize',20);
title('Population Dynamics of log(N) vs. t');

fig2 = figure;
set(gca,'FontSize',20);
plot(1:Tmax,f_vs_t','.-','MarkerSize',14);
xlabel('t','FontSize',20);
ylabel('fraction','FontSize',20);
title('Population Dynamics of fraction vs. t');
legend('age0', 'age1', 'age2','age3');

Fit log(N(t)) to a straight line
p=polyfit(25:Tmax,log(N(1,25:Tmax)),1) % 0.3626  5.8045
lamda_estimate=exp(p(1)) % 1.4371

Numerically solve lamda
Ia = [1 0.5 0.5*0.9 0.5*0.9*0.95] ;
fa = [0 1 5 0.5];
lamda_predicted = fzero(@(lambda) eulot(lambda,Ia,fa),1) % 1.4624 very close.

% test power-positive
n2 = 48;
Apos = A2^(n2^2-n2*2+2);
Aispos = (Apos > 0);
ispos = prod(prod(Aispos)); %1

% calculate dominant eigenvalue lamda
L2=eig(A1);
j2=find(abs(L1)==max(abs(L1)));
dominant_eigenvalue=L2(j2) % 0.9388

%
%% Question 2

% initiate projection matrix A.
l = 0.942*diag(ones(1, 48));
l(1) = 0.0722;
f = 0.24*ones(1,48);
f(1) = 0;
A2 = [f;l(1:47,:)];
E = zeros(48);

% test power-positive
n2 = 48;
Apos = A2^(n2^2-n2*2+2);
Aispos = (Apos > 0);
ispos = prod(prod(Aispos)); %1

% calculate dominant eigenvalue lamda
[Vall,L2,Wall]=eig(A2);
L2 = diag(L2);
j2=find(abs(L2)==max(abs(L2)));
d_lamda = L2(j2); % 0.9388

V = Vall(:,j2);
W = Wall(j2,:);

% calculate elasticity matrix
for x = 1:48
    for y = 1:48
        E(x,y) = A2(x,y)*(V(x)*W(y)/(dot(V, W)*d_lamda));
    end
end

rE = real(E);
surf(real(E));

figure
set(gca,'FontSize',20);
plot(0:46, diag(E(2:48,1:47)),'.-','MarkerSize',20);
xlabel('ages','FontSize',10);
ylabel('survival rate elasticity','FontSize',10);
title('Elasticity for annual survival rate p_a');

figure
set(gca,'FontSize',20);
plot(0:47, E(1,1:48),'.-','MarkerSize',20);
xlabel('ages','FontSize',10);
ylabel('fecundity elasticity','FontSize',10);
title('Elasticity for fecundity f_a');



%% Question 3

% initiate projection matrix A.
A3 = [0 0.0043 0.1132 0;
    0.9755 0.9111 0 0;
    0 0.0736 0.9534 0
    0 0 0.0452 0.9804]
N = 250;

% calculate dominant eigenvalue lamda
[V3, L3]=eig(A3);
L3 = diag(L3);
j3=find(abs(L3) == max(abs(L3)));
dominant_eigenvalue=L3(j3) % 1.0254
w3 = V3(:,j3); %[0.0663;0.5663;0.5793;0.5825]

% simulation of unharvested case
n_zero=w3*N;
Tmax=200;
n_vs_t=zeros(4,Tmax);
n_vs_t(:,1)=n_zero ;
for t=2:Tmax;
    n_vs_t(:,t)=A3*n_vs_t(:,t-1) ;
end

fig1 = figure;
set(gca,'FontSize',20);
plot(1:Tmax,n_vs_t','.-','MarkerSize',14);
xlabel('t','FontSize',20);
ylabel('n','FontSize',20);
title('Killer Whales Dynamics Unharvested');
legend('Y', 'J', 'M','P');

% simulation of harvested case
n_zero=w3*N;
Tmax=200;
n_vs_t_harvested=zeros(4,Tmax);
n_vs_t_breakJ=zeros(4,Tmax);
n_vs_t_breakM=zeros(4,Tmax);
n_vs_t_breakR=zeros(4,Tmax);
n_vs_t_harvested(:,1)=n_zero ;
n_vs_t_breakJ(:,1)=n_zero ;
n_vs_t_breakM(:,1)=n_zero ;
n_vs_t_breakR(:,1)=n_zero ;
Jmost = 0;
Mmost = 0;
RmostSet = [];
RmostJ = [];
RmostM = [];

% find the smallest J possible to get extinction.
% thus the maximum J not to extinct is Jmost - 1
for x = 0:1:100
    h = [0;x;0;0];
    for t=2:Tmax;
        check = prod(((A3*n_vs_t_harvested(:,t-1)-h)>=0));
        n_vs_t_harvested(:,t)=check*(A3*n_vs_t_harvested(:,t-1)-h);
    end
    if (prod((n_vs_t_harvested(:,Tmax)==0))==1)
        Jmost = x; %10, the maximum J not to extinct is Jmost - 1 = 9
        n_vs_t_breakJ(:,:)=n_vs_t_harvested(:,:);
        break;
    end
end

figure;
set(gca,'FontSize',20);
plot(1:Tmax,n_vs_t_breakJ','.-','MarkerSize',14);
xlabel('t','FontSize',20);
ylabel('n','FontSize',20);
title(strcat('Killer Whales Dynamics Harvested w/ Jmost=',num2str(Jmost)));
legend('Y', 'J', 'M','P');

% find the smallest M possible to get extinction.
% thus the maximum M not to extinct is Mmost - 1
for x = 0:1:100
    h = [0;0;x;0];
    for t=2:Tmax;
        check = prod(((A3*n_vs_t_harvested(:,t-1)-h)>=0));
        n_vs_t_harvested(:,t)=check*(A3*n_vs_t_harvested(:,t-1)-h);
    end
    if (prod((n_vs_t_harvested(:,Tmax)==0))==1)
        Mmost = x; %7, the maximum M not to extinct is Mmost - 1 = 6
        n_vs_t_breakM(:,:)=n_vs_t_harvested(:,:);
        break;
    end
end

figure;
set(gca,'FontSize',20);
plot(1:Tmax,n_vs_t_breakM','.-','MarkerSize',14);
xlabel('t','FontSize',20);
ylabel('n','FontSize',20);
title(strcat('Killer Whales Dynamics Harvested w/ Mmost=',num2str(Mmost)));
legend('Y', 'J', 'M','P');

% find the smallest reproductive adults possible to get extinction.
% thus the maximum R not to extinct is Rmost - 1
for x = 0:1:Jmost
    for y = 0:1:100
        h = [0;x;y;0];
        for t=2:Tmax;
            check = prod(((A3*n_vs_t_harvested(:,t-1)-h)>=0));
            n_vs_t_harvested(:,t)=check*(A3*n_vs_t_harvested(:,t-1)-h);
        end
        if (prod((n_vs_t_harvested(:,Tmax)==0))==1)
            RmostSet = [RmostSet x+y];
            RmostJ = [RmostJ x];
            RmostM = [RmostM y];
            break;
        end
    end
end

Rmost = max(RmostSet); % 10, maximum R not to extinct is Rmost - 1 = 9
JRmost = RmostJ(find(RmostSet == Rmost));
MRmost = RmostM(find(RmostSet == Rmost));

% plot each possible J and M breakpoints.
for k = 1:length(JRmost)
    x = JRmost(k);
    y = MRmost(k);
    h = [0;x;y;0];
    for t=2:Tmax;
        check = prod(((A3*n_vs_t_breakR(:,t-1)-h)>=0));
        n_vs_t_breakR(:,t)=check*(A3*n_vs_t_breakR(:,t-1)-h);
    end
    
    figure;
    set(gca,'FontSize',20);
    plot(1:Tmax,n_vs_t_breakR','.-','MarkerSize',14);
    xlabel('t','FontSize',20);
    ylabel('n','FontSize',20);
    title(strcat('Killer Whales Dynamics Harvested w/ Rmost=',num2str(Rmost),',HJ=',num2str(x),',HM=',num2str(y)));
    legend('Y', 'J', 'M','P');
    
end

%% Problem 1 Modified:

sum = 0;
k = 100;
for i = 3:k
    sum = (i+1)/(i-1) + sum
end
sum = k*sum;

x = zeros(1,100);
x(1) = 0;
x(2) = 0;
x(3) = 1;
for n = 1:97
    x(n+3) = x(n+2) + x(n+1) + x(n);
end
ratio = x(100)/x(99);

%% Problem 2 Modified:

x = [exp(1); 5^(1/3); -6*pi; 42];
y = [4; 2; -5; 20];
norm_1 = norm(x, 1);
norm_2 = norm(x, 2);
norm_64 = norm(x, 64);
norm_Inf = norm(x, inf);

dotProd = dot(x, y);
cosTheta = dotProd / (norm(x, 2) * norm(y, 2));
Theta = acos(cosTheta);

%% Problem 3 Modified:

x = linspace(0, 10, 50000);
every40 = 40 * ones(1, length(x));
fx = every40 ./ (1 + (x - 4).^2) + 5 * sin((20 / pi) * x);
plot(x, fx, 'LineWidth', 5);

%% HW2

%% Problem 1:

% Code for Newton Method:
xk = 2;
tol = 1e-6;
for j = 1:100000
    fxk = log(xk) - exp(-3*xk);
    fpxk = 1/xk + 3*exp(-3*xk);
    xkp1 = xk - (fxk / fpxk);
    if (abs(log(xkp1) - exp(-3*xkp1)) < tol)
        break; end
    xk = xkp1; end

% Code for Secant Method:
xkm1 = 2;
xk = 1.7;
tol = 1e-6;
for j = 1:100000
    fxk = log(xk) - exp(-3*xk);
    fxkm1 = log(xkm1) - exp(-3*xkm1);
    xkp1 = xk - (fxk * (xk - xkm1)) / (fxk - fxkm1);
    if (abs(log(xkp1) - exp(-3*xkp1)) < tol)
        break; end
    xkm1 = xk;
    xk = xkp1; end

%% HW 3

% homework 3 starter script

% generate system 1
clear all;
m1 = 300;
n1 = 50;
x1 = randn(n1,1);
SysOne = rand(m1, n1);
bOne = SysOne*x1 + 0.1*randn(m1,1);
alphaOne = 1/(svds(SysOne, 1)^2);

m2 = 2000;
n2 = 200;
x2 = randn(n2, 1);
SysTwo = rand(m2, n2);
bTwo = SysTwo*x2 + 0.1*randn(m2,1);
alphaTwo = 1/(svds(SysTwo, 1)^2);

x1 = zeros(n1,1);
r = -bOne;
g = SysOne'*r;
k = 0;
while(norm(g) > 1e-10)
    x1 = x1 - alphaOne*g;
    g = SysOne'*(SysOne*x1 -bOne);
    % fprintf('norm g is :%5.3e\n', norm(g));
    k = k+1;
end
fprintf('k is: %d\n', k);

x2 = zeros(n2,1);
r = -bTwo;
g = SysTwo'*r;
k = 0;
while(norm(g) > 1e-10)
    x2 = x2 - alphaTwo*g;
    g = SysTwo'*(SysTwo*x2 -bTwo);
    % fprintf('norm g is :%5.3e\n', norm(g));
    k = k+1;
end
fprintf('k is: %d\n', k);

%% Old starter

% homework 3 starter script 

% generate system 1
clear all;
m1 = 300;
n1 = 50; 
x1 = randn(n1,1); 
SysOne = rand(m1, n1); 
bOne = SysOne*x1 + 0.1*randn(m1,1);
alphaOne = 1/(svds(SysOne, 1)^2);


m2 = 2000;
n2 = 200; 
x2 = randn(n2, 1);
SysTwo = rand(m2, n2); 
bTwo = SysTwo*x2 + 0.1*randn(m2,1);
alphaTwo = 1/(svds(SysTwo, 1)^2);

tol = 10e-8;
x(1) = 0; %set
grad(1) = A.'*(A.'*x(1)-b);
for j = 2:1000
    x(j) = x(j-1) - alphaOne*grad(j-1);
    grad(j) = A.'*(A.'*x(j)-b);
    if grad < tol;
         break;
    end
    j = j + 1;
end


tol = 10e-8;
x(1) = 0; 
r(1) = 0;
p(1) = 0;
k = 0;
a(1) = r(1)^2/(A*p(1))^2;
for j = 2:1000
    x(j) = x(j-1) + alphaOne*p(j-1);
    r(j) = r(j) - alphaOne*A.'*A*p(j-1);
    if r(j) < tol;
         break;
    end
    beta(j) = r(j+1)^2/r(j)^2;
    k = k + 1;
    a(j) = r(j)^2/(A*p(j))^2;
    j = j + 1;
end


%% Problem 1a

m1 = 300;
n1 = 50;
x1 = randn(n1,1);
SysOne = rand(m1, n1);
bOne = SysOne*x1 + 0.1*randn(m1,1);
alphaOne = 1/(svds(SysOne, 1)^2);
SysOneTranspose= SysOne.';
gradientOne=500;
error = 1e-3;
iterations = 0;
while abs(norm(gradientOne)) > error
    gradientOne = SysOneTranspose*(SysOne *x1 - bOne);
    x1 = x1 - alphaOne*gradientOne;
    iterations = iterations+1
end

%% Problem 1b

Your code:
m1 = 300;
n1 = 50;
x1 = zeros(n1,1);
SysOne = rand(m1, n1);
bOne = SysOne*x1 + 0.1*randn(m1,1);
SysOneTranspose= SysOne.?;
cOne = SysOneTranspose*bOne;
p0=cOne;
r0=cOne;
error = 1e-3;
iterations = 0;
while abs(norm(r0)) > error
    SysOneP=SysOne*p0;
    alphaK=norm(r0)^2/norm(SysOneP)^2;
    x1 = x1 +alphaK*p0;
    rold=r0;
    r0 = r0 -alphaK*SysOneTranspose*SysOneP;
    betaK = norm(r0)^2/norm(rold)^2;
    p0=r0+betaK*p0;
    iterations=iterations+1
end
