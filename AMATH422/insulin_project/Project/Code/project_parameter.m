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

Tspan=0:0.25:60;
options = odeset('NonNegative',2:7); %make solutions nonnegative

[T,Y] = ode45(@(t,y) sixpool(t,y,p),Tspan,x0,options);

eq=Y(241,:);
internalized=Y(241,4) + Y(241,5) + Y(241,6) + Y(241,7);

for i=1:7

    vary=p;
    count=0;
    
    for k=0:p(i)/100:2*p(i)
        count=count+1;
        vary(i)=k;
        x0 = [0, 100, 0, 0, 0, 0, 0];

        Tspan=0:0.25:60;
        options = odeset('NonNegative',2:7); %make solutions nonnegative

        [T,Y] = ode45(@(t,y) sixpool(t,y,vary),Tspan,x0,options);
        list=[0];
        for x=2:7
            list=[list (Y(241,x)-eq(x)/eq(x))];
        end
        var(count,i,1:7) = list;
    end

% phosphorylated = y(3) + y(4) + y(6)
% internalized and phosphorylated = y(4) + y(6)
% internalized and non-phosphorylated = y(5) + y(7)
% internalized = y(4) + y(5) + y(6) + y(7)
% bound = y(1) + y(2) + y(3) = y(2) + y(3)

end

for x=2:7
    figure
    plot(0:1/100:2,var(:,:,x),'LineWidth',3); hold on;
    legend('p1', 'p2', 'p3', 'p4', 'p5', 'p6', 'p7')
    name=sprintf('x%i', x);
    title(name)
    xlabel('p/p0'); 
    ylabel('% change from equilibrium concentration');
end