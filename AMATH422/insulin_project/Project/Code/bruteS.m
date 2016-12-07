
function update = bruteS(y0, p)

Tmax=80;
delta=0.05;
update = zeros(Tmax/delta+1,7);
update(1,:)=y0;
% y = y;

%k25 = p1
%k32 = p2
%k72 = p3
%k43 = p4
%k64 = p5
%k56 = p6
%k57 = p7


x2= p(2) + p(3);
x3= p(4);
x4= p(5);
x5= p(1);
x6= p(6);
x7= p(7);

x=[0 x2 x3 x4 x5 x6 x7];
for t=1:Tmax/delta
    s=rand();
    if update(t,2)==1
        is3 = delta * p(2) / x2;
        is7 = delta * p(3) / x2;
        if s <= is3
            update(t+1,3)=1;
        elseif s <= is3 + is7
            update(t+1,7)=1;
        else
            update(t+1,2)=1;
        end
    elseif update(t,3)==1
        is4 = delta * x3;
        if s <= is4
            update(t+1,4)=1;
        else
            update(t+1,3)=1;
        end
    elseif update(t,4)==1
        is6 = delta * x4;
        if s <= is6
            update(t+1,6)=1;
        else
            update(t+1,4)=1;
        end
    elseif update(t,6)==1
        is5 = delta * x6;
        if s <= is5
            update(t+1,5)=1;
        else
            update(t+1,6)=1;
        end
    elseif update(t,5)==1
        is2 = delta * x5;
        if s <= is2
            update(t+1,2)=1;
        else
            update(t+1,5)=1;
        end
    else
        is5 = delta * x7;
        if s <= is5
            update(t+1,5)=1;
        else
            update(t+1,7)=1;
        end
    end
end


% y(1) = no insulin, bound, not phosphorylated
% y(2) = insulin, bound, not phosphorylated
% y(3) = insulin, bound, phosphorylated
% y(4) = insulin, internalized, phosphorylated
% y(5) = no insulin, internalized, not phosphorylated
% y(6) = no insulin, internalized, phosphorylated
% y(7) = insulin, internalized, not phosphorylated

% phosphorylated = y(3) + y(4) + y(6)
% internalized and phosphorylated = y(4) + y(6)
% internalized and non-phosphorylated = y(5) + y(7)
% internalized = y(4) + y(5) + y(6) + y(7)
% bound = y(1) + y(2) + y(3) = y(2) + y(3)
