clear all; close all

p1=0.0737;  %k25
p2=1.29;    %k32
p3=0.0411;  %k72
p4=0.0212;  %k43
p5=0.23;    %k64
p6=0.101;   %k56
p7=0.23;    %k57

Nsim = 100
ip = zeros(5000,Nsim);
in = zeros(5000,Nsim);
bd = zeros(5000,Nsim);

rng(1);

for sim = 1:Nsim
    
Nreceptor = 1000;
p = [p1; p2; p3; p4; p5; p6; p7];
x0 = [0; Nreceptor; 0; 0; 0; 0; 0];

Nstep=5000;
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
% 
% figure(1)
% set(gca,'FontSize',16);
% plot(time,100*xall(:,1:7)/Nreceptor,'LineWidth',3);
% legend('x1','x2','x3','x4','x5','x6','x7');
% title('Monte Carlos Stochastic Dynamics of 10000 Receptors vs. time')
% xlabel('time (min)');
% ylabel('% of receptors');

% phosphorylated = y(3) + y(4) + y(6)
% internalized = y(4) + y(5) + y(6) + y(7)

% internalized and phosphorylated = y(4) + y(6)
% internalized and non-phosphorylated = y(5) + y(7)
% bound = y(1) + y(2) + y(3) = y(2) + y(3)

ip(:,sim) = 100*(xall(:,4) + xall(:,6))/Nreceptor;
in(:,sim)  = 100*(xall(:,5) + xall(:,7))/Nreceptor;
bd(:,sim)  = 100*(xall(:,2) + xall(:,3))/Nreceptor;

end

figure(2)
% for sim = sim = 1:50
set(gca,'FontSize',16);
plot3(ip,in,bd,'LineWidth',0.5);hold on
% end
grid on
xlabel('x4 + x6 (internalized/phosphorylated)'); 
ylabel('x5 + x7 (internalized/non-phosphorylated)'); 
zlabel('x1 + x2 + x3 (bound)'); 


