%% HW1 for AMATH422
% @Author: Baihan Lin
% @Date: Oct 2016

clear all; close all; clc;

%
% %% Question 1
%
% % initiate projection matrix A.
% A1 = [0 1 5 0.5;
%     0.5 0 0 0;
%     0 0.9 0 0;
%     0 0 0 0.95];
%
% % simulation of population dynamics
% n_zero=[100; 100; 100; 100];
% f_zero=[0.25;0.25;0.25;0.25];
% Tmax=50;
% n_vs_t=zeros(4,Tmax);
% n_vs_t(:,1)=n_zero ;
% f_vs_t=zeros(4,Tmax);
% f_vs_t(:,1)=f_zero ;
%
% for t=2:Tmax;
%     n_vs_t(:,t)=A1*n_vs_t(:,t-1);
%     n_sum = sum(n_vs_t(:,t));
%     f_vs_t(:,t) = n_vs_t(:,t)/n_sum;
% end
%
% N = sum(n_vs_t);
%
% fig1 = figure;
% set(gca,'FontSize',20);
% plot(1:Tmax,log(N'),'.-','MarkerSize',14);
% xlabel('t','FontSize',20);
% ylabel('log(N)','FontSize',20);
% title('Population Dynamics of log(N) vs. t');
%
% fig2 = figure;
% set(gca,'FontSize',20);
% plot(1:Tmax,f_vs_t','.-','MarkerSize',14);
% xlabel('t','FontSize',20);
% ylabel('fraction','FontSize',20);
% title('Population Dynamics of fraction vs. t');
% legend('age0', 'age1', 'age2','age3');
%
% %Fit log(N(t)) to a straight line
% p=polyfit(25:Tmax,log(N(1,25:Tmax)),1) % 0.3626  5.8045
% lamda_estimate=exp(p(1)) % 1.4371
%
% % Numerically solve lamda
% Ia = [1 0.5 0.5*0.9 0.5*0.9*0.95] ;
% fa = [0 1 5 0.5];
% lamda_predicted = fzero(@(lambda) eulot(lambda,Ia,fa),1) % 1.4624 very close.
%
% % % test power-positive
% % n2 = 48;
% % Apos = A2^(n2^2-n2*2+2);
% % Aispos = (Apos > 0);
% % ispos = prod(prod(Aispos)); %1
% %
% % % calculate dominant eigenvalue lamda
% % L2=eig(A1);
% % j2=find(abs(L1)==max(abs(L1)));
% % dominant_eigenvalue=L2(j2) % 0.9388

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



% %% Question 3
%
% % initiate projection matrix A.
% A3 = [0 0.0043 0.1132 0;
%     0.9755 0.9111 0 0;
%     0 0.0736 0.9534 0
%     0 0 0.0452 0.9804]
% N = 250;
%
% % calculate dominant eigenvalue lamda
% [V3, L3]=eig(A3);
% L3 = diag(L3);
% j3=find(abs(L3) == max(abs(L3)));
% dominant_eigenvalue=L3(j3) % 1.0254
% w3 = V3(:,j3); %[0.0663;0.5663;0.5793;0.5825]
%
% % simulation of unharvested case
% n_zero=w3*N;
% Tmax=200;
% n_vs_t=zeros(4,Tmax);
% n_vs_t(:,1)=n_zero ;
% for t=2:Tmax;
%     n_vs_t(:,t)=A3*n_vs_t(:,t-1) ;
% end
%
% fig1 = figure;
% set(gca,'FontSize',20);
% plot(1:Tmax,n_vs_t','.-','MarkerSize',14);
% xlabel('t','FontSize',20);
% ylabel('n','FontSize',20);
% title('Killer Whales Dynamics Unharvested');
% legend('Y', 'J', 'M','P');
%
% % simulation of harvested case
% n_zero=w3*N;
% Tmax=200;
% n_vs_t_harvested=zeros(4,Tmax);
% n_vs_t_breakJ=zeros(4,Tmax);
% n_vs_t_breakM=zeros(4,Tmax);
% n_vs_t_breakR=zeros(4,Tmax);
% n_vs_t_harvested(:,1)=n_zero ;
% n_vs_t_breakJ(:,1)=n_zero ;
% n_vs_t_breakM(:,1)=n_zero ;
% n_vs_t_breakR(:,1)=n_zero ;
% Jmost = 0;
% Mmost = 0;
% RmostSet = [];
% RmostJ = [];
% RmostM = [];
%
% % find the smallest J possible to get extinction.
% % thus the maximum J not to extinct is Jmost - 1
% for x = 0:1:100
%     h = [0;x;0;0];
%     for t=2:Tmax;
%         check = prod(((A3*n_vs_t_harvested(:,t-1)-h)>=0));
%         n_vs_t_harvested(:,t)=check*(A3*n_vs_t_harvested(:,t-1)-h);
%     end
%     if (prod((n_vs_t_harvested(:,Tmax)==0))==1)
%         Jmost = x; %10, the maximum J not to extinct is Jmost - 1 = 9
%         n_vs_t_breakJ(:,:)=n_vs_t_harvested(:,:);
%         break;
%     end
% end
%
% figure;
% set(gca,'FontSize',20);
% plot(1:Tmax,n_vs_t_breakJ','.-','MarkerSize',14);
% xlabel('t','FontSize',20);
% ylabel('n','FontSize',20);
% title(strcat('Killer Whales Dynamics Harvested w/ Jmost=',num2str(Jmost)));
% legend('Y', 'J', 'M','P');
%
% % find the smallest M possible to get extinction.
% % thus the maximum M not to extinct is Mmost - 1
% for x = 0:1:100
%     h = [0;0;x;0];
%     for t=2:Tmax;
%         check = prod(((A3*n_vs_t_harvested(:,t-1)-h)>=0));
%         n_vs_t_harvested(:,t)=check*(A3*n_vs_t_harvested(:,t-1)-h);
%     end
%     if (prod((n_vs_t_harvested(:,Tmax)==0))==1)
%         Mmost = x; %7, the maximum M not to extinct is Mmost - 1 = 6
%         n_vs_t_breakM(:,:)=n_vs_t_harvested(:,:);
%         break;
%     end
% end
%
% figure;
% set(gca,'FontSize',20);
% plot(1:Tmax,n_vs_t_breakM','.-','MarkerSize',14);
% xlabel('t','FontSize',20);
% ylabel('n','FontSize',20);
% title(strcat('Killer Whales Dynamics Harvested w/ Mmost=',num2str(Mmost)));
% legend('Y', 'J', 'M','P');
%
% % find the smallest reproductive adults possible to get extinction.
% % thus the maximum R not to extinct is Rmost - 1
% for x = 0:1:Jmost
%     for y = 0:1:100
%         h = [0;x;y;0];
%         for t=2:Tmax;
%             check = prod(((A3*n_vs_t_harvested(:,t-1)-h)>=0));
%             n_vs_t_harvested(:,t)=check*(A3*n_vs_t_harvested(:,t-1)-h);
%         end
%         if (prod((n_vs_t_harvested(:,Tmax)==0))==1)
%             RmostSet = [RmostSet x+y];
%             RmostJ = [RmostJ x];
%             RmostM = [RmostM y];
%             break;
%         end
%     end
% end
%
% Rmost = max(RmostSet); % 10, maximum R not to extinct is Rmost - 1 = 9
% JRmost = RmostJ(find(RmostSet == Rmost));
% MRmost = RmostM(find(RmostSet == Rmost));
%
% % plot each possible J and M breakpoints.
% for k = 1:length(JRmost)
%     x = JRmost(k);
%     y = MRmost(k);
%     h = [0;x;y;0];
%     for t=2:Tmax;
%         check = prod(((A3*n_vs_t_breakR(:,t-1)-h)>=0));
%         n_vs_t_breakR(:,t)=check*(A3*n_vs_t_breakR(:,t-1)-h);
%     end
%
%     figure;
%     set(gca,'FontSize',20);
%     plot(1:Tmax,n_vs_t_breakR','.-','MarkerSize',14);
%     xlabel('t','FontSize',20);
%     ylabel('n','FontSize',20);
%     title(strcat('Killer Whales Dynamics Harvested w/ Rmost=',num2str(Rmost),',HJ=',num2str(x),',HM=',num2str(y)));
%     legend('Y', 'J', 'M','P');
%
% end
%
%
%
%
%
%



