close all; clear all;

p1=0.0737; %k25
p2=1.29; %k32
p3=0.0411; %k72
p4=0.0212; %k43
p5=0.23; %k64
p6=0.101; %k56
p7=0.23; %k57

delta=0.05;
Tmax=80;

p = [p1 p2 p3 p4 p5 p6 p7];
x0 = [0, 1, 0, 0, 0, 0, 0];
tot = zeros(Tmax/delta+1,7);
dwell=zeros(Tmax/delta+1,100);
y=zeros(Tmax/delta+1,7);

for receptors=1:1000
    y = bruteS(x0, p);
    tot = tot + y;
    
    for t=1:(Tmax/delta+1)
        k=find(y(t,:) == 1);
        dwell(t,receptors)=k;
    end
end

%%
figure
set(gca,'FontSize',16)
plot(0:delta:Tmax, tot/10,'LineWidth',3);
title('1000 receptors')
xlabel('time (min)'); 
ylabel('% of receptors');
legend('x1', 'x2', 'x3', 'x4', 'x5', 'x6', 'x7');

%% dwell times

dwelltimes=zeros(7,1);
list2=[];
list3=[];
list4=[];
list5=[];
list6=[];
list7=[];

for receptors=1:1000
    value=dwell(1,receptors);
    time=1;
    for t=1:(Tmax/delta)
        next = dwell(t+1,receptors);
        if next ~= value
            realdwell = time*delta;
            if value == 2
                list2 = [list2 realdwell];
            elseif value == 3
                list3 = [list3 realdwell];
            elseif value == 4
                list4 = [list4 realdwell];
            elseif value == 5
                list5 = [list5 realdwell];
            elseif value == 6
                list6 = [list6 realdwell];
            else
                list7 = [list7 realdwell];
            time=1;
            end
        else
            time = time + 1;
        end
        if t==(Tmax/delta)
            realdwell = time*delta;
            if value == 2
                list2 = [list2 realdwell];
            elseif value == 3
                list3 = [list3 realdwell];
            elseif value == 4
                list4 = [list4 realdwell];
            elseif value == 5
                list5 = [list5 realdwell];
            elseif value == 6
                list6 = [list6 realdwell];
            else
                list7 = [list7 realdwell];
            end
        end
        value = next;
    end
end

    dwelltimes(2,1:length(list2)) = list2;
    dwelltimes(3,1:length(list3)) = list3;
    dwelltimes(4,1:length(list4)) = list4;
    dwelltimes(5,1:length(list5)) = list5;
    dwelltimes(6,1:length(list6)) = list6;
    dwelltimes(7,1:length(list7)) = list7;

for index=2:7
    xmax=max(dwelltimes(index,:));
    pIndex = dwelltimes(index,:) > 0;
    [nlist,centerlist]=hist(dwelltimes(index,pIndex),50);
    ymax=max(nlist);
    
    figure(1)
    subplot(3,2,index-1)
    bar(centerlist,nlist)
    axis([0.1 xmax+1 0 ymax])
    name=sprintf('x%i', index);
    title(name)
    xlabel('Dwell time (min)')
    ylabel('Frequency')
end