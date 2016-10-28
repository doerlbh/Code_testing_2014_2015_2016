%  runs TVO.m
% k1 is for mRNA production (D=D+M)
% k2 is for protein production (M=M+P)
% k3 is for M decay
% k4 is for P decay
tspan=[0 40000];
yzero=[0;0];
[ta,ya]=ode45(@TVO,tspan,yzero);
plot(ta,ya(:,2),'-*')
title('Number of proteins as a function of time')
xlabel('time in seconds')
ylabel('number of P')
legend('b=2',4)
hold
% at steady state
steadysstateprotein=ya(length(ta),2)
steadystatemRNA=ya(length(ta),1)

