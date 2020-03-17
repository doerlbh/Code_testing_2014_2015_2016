% RNA interference modeled by Monte Carlos Simulation
% Author: Baihan Lin
% Date: Nov 2016

clear all; close all

gm1 = 0.005;
gm2 = 0.01;
gm3 = 0.006;
K1 = 1;
K2 = 0.8;
K3 = 0.9;
H = 0.5;
f = 0.3;

% ep1 = gm2/gm3;
% ep2 = gm1/gm3;
% H1 = H*K1/(gm1*gm3);
% H2 = H*K2/(gm1*gm3);
% H3 = H^2*ep1*K1*K2/(gm2^2);

p = [gm1, gm2, gm3, K1, K2, K3, H, f];
x0 = [0; 0; 0; 0];

% rng(1);

Nstep=50000;
stptime = zeros(Nstep,1);
time = zeros(Nstep,1);
xall = zeros(Nstep,4);
xall(1,:) = x0;
x = x0;

for step = 1 : Nstep - 1
   [xnew, tau] = stoch_update(x, p);
   x = xnew;
   stptime(step+1) = tau;
   time(step+1) = time(step) + tau;
   xall(step+1,:) = x;
end

figure(1)
plot(time,xall(:,1:4),'LineWidth',1.5);
legend('x (iRNA)','y (mRNA)','z (mRNA.iRNA.RISC)','w (protein)');
xlabel('time (s)'); 
% 
% figure(2)
% plot(1:Nstep,xall(:,1:4),'LineWidth',1.5);
% legend('x (iRNA)','y (mRNA)','z (mRNA.iRNA.RISC)','w (protein)');
% xlabel('steps'); 

figure(3)
plot3(xall(:,1),xall(:,2),xall(:,3),'LineWidth',1.5);
grid on
xlabel('iRNA'); 
ylabel('mRNA'); 
zlabel('protein'); 