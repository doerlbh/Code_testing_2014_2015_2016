%% HW3 for AMATH422
% @Author: Baihan Lin
% @Date: Nov 2016

clear all; close all; clc;

rng(1);

%% I. Coupled oscillators
%
% close all
%
% alpha=15;
%
% alpha0=0.5;
% beta=0.2;
% n=2;
% gamma=10;
% % gamma=-10;
%
% for numreps=1:10
%
%     p = [alpha,alpha0,beta,n,gamma];
%     x0 = 30*rand(12,1) ;
%
%     Tmax=200;
%
%     [T,Y] = ode45(@coupleOsci,[0 Tmax],x0,[],p);
%
%     figure(1)
%     set(gca,'FontSize',16)
%     plot(T,Y(:,1:3),'LineWidth',2) ; hold on
%     plot(T,Y(:,7:9),'LineWidth',2) ; hold on;
%     legend('m lalcl','m tetR','m cl','n lalcl','n tetR','n cl')
%     xlabel('t') ;
%     title(strcat('mRNA vs. t when gamma = ',num2str(gamma)));
%
%     figure(2)
%     set(gca,'FontSize',16)
%     plot(T,Y(:,4:6),'LineWidth',2) ; hold on
%     plot(T,Y(:,10:12),'LineWidth',2) ; hold on;
%     legend('p lacl','p tetR','p cl','q lacl','q tetR','q cl')
%     xlabel('t') ;
%     title(strcat('protein vs. t when gamma = ',num2str(gamma)));
%
%     figure(3)
%     set(gca,'FontSize',16)
%     plot3(Y(:,1),Y(:,2),Y(:,3),'LineWidth',2) ; hold on
%     xlabel('m lalcl');ylabel('m tetR');zlabel('m cl');
%     title(strcat('m mRNA relationship when gamma = ',num2str(gamma)));
%
%     figure(4)
%     set(gca,'FontSize',16)
%     plot3(Y(:,7),Y(:,8),Y(:,9),'LineWidth',2) ; hold on
%     xlabel('m lalcl');ylabel('m tetR');zlabel('m cl');
%     title(strcat('n mRNA relationship when gamma = ',num2str(gamma)));
%
%     figure(5)
%     set(gca,'FontSize',16)
%     plot3(Y(:,4),Y(:,5),Y(:,6),'LineWidth',2) ; hold on
%     xlabel('p lalcl');ylabel('p tetR');zlabel('p cl');
%     title(strcat('p protein relationship when gamma = ',num2str(gamma)));
%
%     figure(6)
%     set(gca,'FontSize',16)
%     plot3(Y(:,10),Y(:,11),Y(:,12),'LineWidth',2) ; hold on
%     xlabel('q lalcl');ylabel('q tetR');zlabel('q cl');
%     title(strcat('q protein relationship when gamma = ',num2str(gamma)));
%
% end

%% II. Systems Biology and Network Motifs

close all

alpha1=0.1;
alpha2=0.1;
n=1;
% ctrl=1; % positive
ctrl=0; % negative
alpha3=0.001;
beta=0.01;

for numreps=1:1
    
    p = [alpha1,alpha2,n,ctrl,alpha3,beta];
    %         x0 = [1; 0; 0];
    x0 = [1; 0; 1];
    
    Tmax=2000;
    
    [T,Y] = ode45(@doubleCtrl,[0 Tmax],x0,[],p);
    
    X1 = Y(:,2)./max(Y(:,2));
    Y1 = Y(:,3)./max(Y(:,3));
    Z1 = [Y(:,1) > 500] - [Y(:,1) > 1000];
    
    figure(1)
    set(gca,'FontSize',16)
    
    subplot(3,1,1)
    plot(T,X1,'LineWidth',2) ; hold on
    ylabel('X');
    xlabel('t');
    ylim([0 1]);
    %     title('X vs. t in double positive control');
    title('X vs. t in double negative control');
    
    subplot(3,1,2)
    plot(T,Y1,'LineWidth',2) ; hold on
    ylabel('Y');
    xlabel('t');
    ylim([0 1]);
    %     title('Y vs. t in double positive control');
    title('Y vs. t in double negative control');
    
    subplot(3,1,3)
    plot(T,Z1,'LineWidth',2) ; hold on
    ylabel('Z');
    xlabel('t');
    ylim([0 1]);
    %     title('Z vs. t in double positive control');
    title('Z vs. t in double negative control');
    
end

