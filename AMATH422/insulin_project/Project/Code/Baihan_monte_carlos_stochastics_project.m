clear all; close all

p1=0.0737;  %k25
p2=1.29;    %k32
p3=0.0411;  %k72
p4=0.0212;  %k43
p5=0.23;    %k64
p6=0.101;   %k56
p7=0.23;    %k57

p = [p1; p2; p3; p4; p5; p6; p7];
x0 = [0; 100; 0; 0; 0; 0; 0];

rng(1);

Nstep=60;
stptime = zeros(Nstep,1);
time = zeros(Nstep,1);
xall = zeros(Nstep,7);
xall(1,:) = x0;
x = x0;

for step = 1 : Nstep - 1
   [xnew, tau, rate] = stochastic_update_coupled(x, p);
   x = xnew;
   stptime(step+1) = tau;
   time(step+1) = time(step) + tau;
   xall(step+1,:) = x;
end

%plot phosphorylated vs non-phosphorylated
figure(1)
set(gca,'FontSize',16)
subplot(2,1,1)
plot(time,xall(:,3:4),'LineWidth',3); hold on;
plot(time,xall(:,6),'LineWidth',3); hold on;
legend('x3','x4','x6')
title('Stochastic Dynamics of Phosphorylated Receptor vs. time')
xlabel('time (min)'); 
ylabel('%');

subplot(2,1,2)
plot(time,xall(:,2),'LineWidth',3); hold on;
plot(time,xall(:,5),'LineWidth',3); hold on;
plot(time,xall(:,7),'LineWidth',3); hold on;
legend('x2','x5','x7')
title('Stochastic Dynamics of Nonphosphorylated Receptor vs. time')
xlabel('time (min)'); 
ylabel('%');

% phosphorylated = y(3) + y(4) + y(6)
figure(2)
set(gca,'FontSize',16)
plot(time,(xall(1:Nstep,3) + xall(1:Nstep,4) + xall(1:Nstep,6)),'LineWidth',3); hold on;
legend('x2')
title('Stochastic Dynamics of Phosphorylated Receptor vs. time')
xlabel('time (min)'); 
ylabel('%');

% internalized and phosphorylated = y(4) + y(6)
% internalized and non-phosphorylated = y(5) + y(7)
% internalized = y(4) + y(5) + y(6) + y(7)
% bound = y(1) + y(2) + y(3) = y(2) + y(3)

%plot phosphorylated vs non-phosphorylated
figure(3)
set(gca,'FontSize',16)
subplot(2,1,1)
plot(1:Nstep,xall(:,3:4),'LineWidth',3); hold on;
plot(1:Nstep,xall(:,6),'LineWidth',3); hold on;
legend('x3','x4','x6')
title('Stochastic Dynamics of Phosphorylated Receptor vs. simulation step')
xlabel('simulation step'); 
ylabel('%');

subplot(2,1,2)
plot(1:Nstep,xall(:,2),'LineWidth',3); hold on;
plot(1:Nstep,xall(:,5),'LineWidth',3); hold on;
plot(1:Nstep,xall(:,7),'LineWidth',3); hold on;
legend('x2','x5','x7')
title('Stochastic Dynamics of Nonphosphorylated Receptor vs. simulation step')
xlabel('simulation step'); 
ylabel('%');

% phosphorylated = y(3) + y(4) + y(6)
figure(4)
set(gca,'FontSize',16)
plot(1:Nstep,(xall(1:Nstep,3) + xall(1:Nstep,4) + xall(1:Nstep,6)),'LineWidth',3); hold on;
legend('x2')
title('Stochastic Dynamics of Phosphorylated Receptor vs. simulation step')
xlabel('simulation step'); 
ylabel('%');

figure(5)
set(gca,'FontSize',16)
plot(1:Nstep, stptime,'LineWidth',3); 
title('waiting time for each step happening')
xlabel('simulation step'); 
ylabel('waiting time for each step (min)');

figure(6)
set(gca,'FontSize',16)
plot(1:Nstep, time,'LineWidth',3);
title('cumulative time of each step happening')
xlabel('simulation step'); 
ylabel('time elapse (s)');
