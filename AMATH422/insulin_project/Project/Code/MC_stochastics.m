clear all; close all

p1=0.0737;  %k25
p2=1.29;    %k32
p3=0.0411;  %k72
p4=0.0212;  %k43
p5=0.23;    %k64
p6=0.101;   %k56
p7=0.23;    %k57
% 
% Nreceptor = 100;
% p = [p1; p2; p3; p4; p5; p6; p7];
% x0 = [0; Nreceptor; 0; 0; 0; 0; 0];
% 
% rng(1);
% 
% Nstep=1000;
% stptime = zeros(Nstep,1);
% time = zeros(Nstep,1);
% xall = zeros(Nstep,7);
% xall(1,:) = x0;
% x = x0;
% 
% for step = 1 : Nstep - 1
%    [xnew, tau] = MC_stochastic_update(x, p);
%    x = xnew;
%    stptime(step+1) = tau;
%    time(step+1) = time(step) + tau;
%    xall(step+1,:) = x;
% end
% 
% figure(1)
% set(gca,'FontSize',30);
% plot(time,100*xall(:,1:7)/Nreceptor,'LineWidth',3);
% legend('x1','x2','x3','x4','x5','x6','x7');
% % title('Monte Carlos Stochastic Dynamics of 10000 Receptors vs. time')
% xlabel('time (min)'); 
% ylabel('% of receptors');
% % 
% figure(2)
% set(gca,'FontSize',30);
% plot(1:length(stptime),stptime,'LineWidth',3);
% ylabel('time (min)'); 
% xlabel('simulation step');
% 
% figure(3)
% set(gca,'FontSize',30);
% plot(1:length(time),time,'LineWidth',3);
% ylabel('time (min)'); 
% xlabel('simulation step');
% 
times = zeros(1,5);

for N = 1:5
    
Nir = 10^N;
p = [p1; p2; p3; p4; p5; p6; p7];
x0 = [0; Nir; 0; 0; 0; 0; 0];

rng(1);

Nstep=Nir*10;
stptime = zeros(Nstep,1);
time = zeros(Nstep,1);
xall = zeros(Nstep,7);
xall(1,:) = x0;
x = x0;

for step = 1 : Nstep - 1
   [xnew, tau] = MC_stochastic_update(x, p);
   x = xnew;
   stptime(step+1) = tau;
   time(step+1) = time(step) + tau;
   xall(step+1,:) = x;
end

times(N) = mean(stptime);

end

%%
figure(4)
set(gca,'FontSize',100);
plot((1:5),log(times),'LineWidth',3);
ylabel('log of mean waiting time (log(min))'); 
xlabel('log10(number of receptor)');

