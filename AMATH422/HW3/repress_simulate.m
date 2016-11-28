% Simulate the repressilator model

close all

alpha=150;

alpha0=0.5;
beta=10.2;
n=2;

for numreps=1:1

p = [alpha,alpha0,beta,n];
x0 = 30*rand(6,1) ;

Tmax=40;

[T,Y] = ode45(@repress,[0 Tmax],x0,[],p);

figure(1)
set(gca,'FontSize',16)
plot(T,Y(:,1:3),'LineWidth',3) ; hold on
plot(T,Y(:,4:6),'LineWidth',3) ; hold on;
legend('m lalcl','m tetR','m cl','p lacl','p tetR','p cl')
xlabel('t') ; 

figure(2)
set(gca,'FontSize',16)
plot3(Y(:,1),Y(:,2),Y(:,3),'LineWidth',3) ; hold on 
xlabel('m lalcl');ylabel('m tetR');zlabel('m cl')

end