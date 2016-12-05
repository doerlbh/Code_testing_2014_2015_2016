clear all; close all

p1=0.0737;  %k25
p2=1.29;    %k32
p3=0.0411;  %k72
p4=0.0212;  %k43
p5=0.23;    %k64
p6=0.101;   %k56
p7=0.23;    %k57

var=zeros(201,7);
%parameter = 0:200:2p(i)

p = [p1 p2 p3 p4 p5 p6 p7];
x0 = [0, 100, 0, 0, 0, 0, 0];

rng(1);

Nstep=10000;
time = zeros(Nstep,1);
xall = zeros(Nstep,7);
xall(1,:) = x0;
x = x0;

for step = 1 : Nstep - 1
   [xnew, tau, rate] = stochastic_update(x, p);
   x = xnew;
   time(step+1) = tau;
   xall(step+1,:) = x;
end

%plot phosphorylated vs non-phosphorylated
figure(1)
set(gca,'FontSize',16)
subplot(2,1,1)
plot(time,xall(:,3:4),'LineWidth',3); hold on;
plot(time,xall(:,6),'LineWidth',3); hold on;
legend('x3','x4','x6')
title('Stochastic Dynamics of Phosphorylated')
xlabel('t'); 
ylabel('%');

subplot(2,1,2)
plot(time,xall(:,2),'LineWidth',3); hold on;
plot(time,xall(:,5),'LineWidth',3); hold on;
plot(T,Y(:,7),'LineWidth',3); hold on;
legend('x2','x5','x7')
title('Stochastic Dynamics of Nonphosphorylated')
xlabel('t'); 
ylabel('%');

% phosphorylated = y(3) + y(4) + y(6)
s=xall(:,4)+xall(:,6);
t=sum(xall(:,:),2);
figure
plot(1:60,(xall(1:60,3) + xall(1:60,4) + xall(1:60,6)),'LineWidth',3); hold on;
legend('x2')
title('Phosphorylated')
xlabel('t'); 
ylabel('%');

% internalized and phosphorylated = y(4) + y(6)
% internalized and non-phosphorylated = y(5) + y(7)
% internalized = y(4) + y(5) + y(6) + y(7)
% bound = y(1) + y(2) + y(3) = y(2) + y(3)


end