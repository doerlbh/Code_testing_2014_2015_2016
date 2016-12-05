
function [ynew, tau, dy] = stochastic_update(y, p)

dy = zeros(7,1);
ynew = y;

dy(1) = 0;
dy(2) = p(1)*y(5) - (p(2) + p(3))*y(2);
dy(3) = p(2)*y(2) - p(4)*y(3);
dy(4) = p(4)*y(3) - p(5)*y(4);
dy(5) = p(6)*y(6) + p(7)*y(7) - p(1)*y(5);
dy(6) = p(5)*y(4) - p(6)*y(6);
dy(7) = p(3)*y(2) - p(7)*y(7);

tau = log(1/rand()) / sum(dy);

r = rand()*sum(dy);
if r < sum(dy(1))
    ynew(x) ;
else
    states(k+1)=2;
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
