% RNA interference modeled by Deterministic ODE
% Author: Baihan Lin
% Date: Nov 2016

clear all; close all

gm1 = 0.3;
gm2 = 0.8;
gm3 = 0.2;
K1 = 0.3;
K2 = 0.5;
H = 0.5;

ep1 = gm2/gm3;
ep2 = gm1/gm3;
H1 = H*K1/(gm1*gm3);
H2 = H*K2/(gm1*gm3);
H3 = H^2*ep1*K1*K2/(gm2^2);

%% Dynamic Simulation

for numreps=1:1

p = [ep1, ep2, H1, H2, H3];
x0 = [0, 0, 0, 0];

Tmax=5;
options = odeset('NonNegative',1:4); %make solutions nonnegative

[T,Y] = ode45(@(t,y) iRNA_ODE(t,y,p),[0 Tmax],x0,options);

figure(1)
subplot(2,1,1);
plot(T,Y(:,1:2),'LineWidth',1.5);
legend('v (iRNA)','r (mRNA)')
xlabel('t'); 
ylabel('nondimensionalized mRNA/iRNA');

subplot(2,1,2);
plot(T,Y(:,4),'LineWidth',1.5); hold on;
legend('s (protein)')
xlabel('t'); 
ylabel('nondimensionalized protein');

figure(2)
grid on;
set(gca,'FontSize',16)
plot3(Y(:,1),Y(:,2),Y(:,4),'LineWidth',1.5);
xlabel('v (iRNA)'); 
ylabel('r (mRNA)');
zlabel('s (protein)');

end

%% Steady States Search

v = 0:0.01:1;
r = 0:0.01:1;
vpre = 1+H1*r;
rpre = 1+H2*v;
vnull = 1./vpre;
rnull = 1./rpre;

v_star = (H2-H1-1+sqrt((H2-H1-1)^2+4*H2))/(2*H2);
r_star = (H1-H2-1+sqrt((H1-H2-1)^2+4*H1))/(2*H1);

figure(3)
plot(v, rnull,'LineWidth',1.5);hold on
plot(vnull,r,'LineWidth',1.5);
plot(v, r_star*ones(1,length(v)), '.r');
plot(v_star*ones(1,length(v)), r, '.r');
plot(v_star,r_star,'o');
legend('r nullcline', 'v nullcline');
xlabel('v (iRNA)'); 
ylabel('r (mRNA)');
