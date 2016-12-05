%solve where all differntial equations are zero
clear all; close all;
%runtime on my computer was 44.3 seconds
syms p1 p2 p3 p4 p5 p6 p7 y1 y2 y3 y4 y5 y6 y7;

dy1 = 0;
dy2 = p1*y5 - (p2 + p3)*y2;
dy3 = p2*y2 - p4*y3;
dy4 = p4*y3 - p5*y4;
dy5 = p6*y6 + p7*y7 - p1*y5;
dy6 = p5*y4 - p6*y6;
dy7 = p3*y2 - p7*y7;
eqns=[p1*y5 - (p2 + p3)*y2==0,p2*y2 - p4*y3==0,p4*y3 - p5*y4==0,p6*y6 + p7*y7 - p1*y5==0,p5*y4 - p6*y6==0,p3*y2 - p7*y7==0];%,y1,y2,y3,y4,y5,y6,y7);
vars=[y1,y2,y3,y4,y5,y6,y7];
[a b c d e f g params conditions]=solve(eqns,vars,'ReturnConditions',true);
sys=[a b c d e f g];


%%
%solve z parameters and such....
%    p(1)=k25
%    p(2)=k32
%    p(3)=k72
%    p(4)=k43
%    p(5)=k64
%    p(6)=k56
%    p(7)=k57
z=0;
total_pop=100;
p = [0.0737 1.29 0.0411 0.0212 0.23 0.101 0.23];
p1=p(1);p2=p(2);p3=p(3);p4=p(4);p5=p(5);p6=p(6);p7=p(7);

z1=solve(subs(sum(sys))==total_pop);


Yeq=double(subs(sys))

%%
%total system derivative
derivatives=zeros(length(p),1);
for i=1:length(p)
    syms p1 p2 p3 p4 p5 p6 p7;
    parameters=[p1 p2 p3 p4 p5 p6 p7];
    d=diff(sum(sys),parameters(i));
    p = [0.0737 1.29 0.0411 0.0212 0.23 0.101 0.23];
    p1=p(1);p2=p(2);p3=p(3);p4=p(4);p5=p(5);p6=p(6);p7=p(7);
    derivatives(i)=double(subs(d));
end
figure
bar((derivatives))
set(gca,'XTickLabel',{'p1', 'p2', 'p3', 'p4','p5','p6','p7'})
ylabel('Derivative of equilibirum solution with respect to each parameter')

%%
derivatives=zeros(length(p),7);
for i=1:length(p)
    syms p1 p2 p3 p4 p5 p6 p7;
    parameters=[p1 p2 p3 p4 p5 p6 p7];
    d=diff((sys),parameters(i));
    p = [0.0737 1.29 0.0411 0.0212 0.23 0.101 0.23];
    p1=p(1);p2=p(2);p3=p(3);p4=p(4);p5=p(5);p6=p(6);p7=p(7);
    derivatives(i,:)=double(subs(d));
end
figure
for x=2:7  
    subplot(3,2,x-1)
    bar(derivatives(:,x)); 
    name=sprintf('x%i', x);
    title(name)
    set(gca,'XTickLabel',{'p1', 'p2', 'p3', 'p4','p5','p6','p7'})
    xlabel('parameter')
    ylabel('derivative of equilibirum state')
end




%%
clear p;clear var;
var=zeros(201,7,7);

for i=1:7
    p = [0.0737 1.29 0.0411 0.0212 0.23 0.101 0.23];
    
    for j=1:201
        p = [0.0737 1.29 0.0411 0.0212 0.23 0.101 0.23];
        p(i)=((j)/100)*p(i);
        p1=p(1);p2=p(2);p3=p(3);p4=p(4);p5=p(5);p6=p(6);p7=p(7);
        var(j,i,:)=(double(subs(sys))-Yeq)./Yeq;
    end
end
  
for i=1:(size(var,1)*size(var,2)*size(var,3))
    if(isnan(var(i)))
        var(i)=0;
    end
end


%%
figure(5)
for x=2:7
    
    subplot(3,2,x-1)
    plot(0:1/100:2,var(:,:,x),'LineWidth',3); hold on;
    if(x==2)
        legend('p1', 'p2', 'p3', 'p4', 'p5', 'p6', 'p7')
        ylabel('% change from equilibrium concentration');
    
    end
    name=sprintf('x%i', x);
    title(name)
    xlabel('p/p0'); 
    axis([0 2 -10 100])
end