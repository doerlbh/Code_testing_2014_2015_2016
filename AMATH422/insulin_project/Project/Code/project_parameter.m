clear all; close all

p1=0.0737; %k25
p2=1.29; %k32
p3=0.0411; %k72
p4=0.0212; %k43
p5=0.23; %k64
p6=0.101; %k56
p7=0.23; %k57

var=zeros(201,7);
%parameter = 0:200:2p(i)

p = [p1 p2 p3 p4 p5 p6 p7];
x0 = [0, 100, 0, 0, 0, 0, 0];
list=[];

Tspan=0:0.25:200;
options = odeset('NonNegative',2:7); %make solutions nonnegative

[T,Y] = ode45(@(t,y) sixpool(t,y,p),Tspan,x0,options);

eq=Y(801,:);
internalized=Y(801,4) + Y(801,5) + Y(801,6) + Y(801,7);

for i=1:7

    vary=p;
    count=0;
    
    for k=0:p(i)/100:2*p(i)
        count=count+1;
        vary(i)=k;
        x0 = [0, 100, 0, 0, 0, 0, 0];

        Tspan=0:0.25:200;
        options = odeset('NonNegative',2:7); %make solutions nonnegative

        [T,Y] = ode45(@(t,y) sixpool(t,y,vary),Tspan,x0,options);
        list=[0];
        for x=2:7
            list=[list (Y(801,x)-eq(x))];
        end
        var(count,i,1:7) = list;
    end

end

%%
% phosphorylated = y(3) + y(4) + y(6)
figure
plot(0:1/100:2,100*(var(:,:,3) + var(:,:,4) + var(:,:,6))/(eq(3)+eq(4)+eq(6)),'LineWidth',3);hold on;
legend('k25', 'k32', 'k72', 'k43', 'k64', 'k56', 'k57')
title('Phosphorylated')
xlabel('p/p0'); 
ylabel('% change from equilibrium concentration');
% internalized and phosphorylated = y(4) + y(6)
% internalized and non-phosphorylated = y(5) + y(7)
% internalized = y(4) + y(5) + y(6) + y(7)
figure
plot(0:1/100:2,100*(var(:,:,4) + var(:,:,5) + var(:,:,6) + var(:,:,7))/(eq(4)+eq(5)+eq(6)+eq(7)),'LineWidth',3);hold on;
legend('k25', 'k32', 'k72', 'k43', 'k64', 'k56', 'k57')
title('Internalized')
xlabel('p/p0'); 
ylabel('% change from equilibrium concentration');
% bound = y(1) + y(2) + y(3) = y(2) + y(3)

for x=2:7
    figure
    plot(0:1/100:2,var(:,:,x)*100/eq(x),'LineWidth',3); hold on;
    legend('k25', 'k32', 'k72', 'k43', 'k64', 'k56', 'k57')
    name=sprintf('x%i', x);
    title(name)
    xlabel('p/p0'); 
    ylabel('% change from equilibrium concentration');
end