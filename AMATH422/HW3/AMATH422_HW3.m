%% HW3 for AMATH422
% @Author: Baihan Lin
% @Date: Nov 2016

clear all; close all; clc;

rng(1);


close all

alpha=150;

alpha0=0.5;
beta=10.2;
n=2;
% gamma=10;
gamma=-10;

for numreps=1:50

p = [alpha,alpha0,beta,n,gamma];
x0 = 30*rand(6,1) ;

Tmax=10;

[T,Y] = ode45(@coupleOsci,[0 Tmax],x0,[],p);

figure(1)
set(gca,'FontSize',16)
plot(T,Y(:,1:2),'LineWidth',2) ; hold on
% plot(T,Y(:,3:4),'LineWidth',2) ; hold on;
legend('m','n')
xlabel('t') ; 
title(strcat('mRNA vs. t when gamma = ',num2str(gamma)));

figure(2)
set(gca,'FontSize',16)
% plot(T,Y(:,1:2),'LineWidth',2) ; hold on
plot(T,Y(:,3:4),'LineWidth',2) ; hold on;
legend('p','q')
xlabel('t') ; 
title(strcat('protein vs. t when gamma = ',num2str(gamma)));

figure(3)
set(gca,'FontSize',16)
plot(Y(:,1),Y(:,2),'LineWidth',2) ; hold on 
xlabel('m');ylabel('n');
title(strcat('mRNA relationship when gamma = ',num2str(gamma)));

figure(4)
set(gca,'FontSize',16)
plot(Y(:,3),Y(:,4),'LineWidth',2) ; hold on 
xlabel('p');ylabel('q');
title(strcat('protein relationship when gamma = ',num2str(gamma)));

end