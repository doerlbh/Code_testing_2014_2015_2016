
function [ynew, tau, dy] = new_stochastic_update(y, p)

k25 = p(1);
k32 = p(2);
k72 = p(3);
k43 = p(4);
k64 = p(5);
k56 = p(6);
k57 = p(7);

rates = [k32 * y(2);
    k43 * x(3);
    k64 * x(4);
    k56 * x(6);
    k25 * x(5);
    k72 * x(2);
    k57 * x(7);]

lambda = sum(rates);

Trans = [0 0 0 0 0 0 0;
    -1 0 0 0 1 -1 0;
    1 -1 0 0 0 0 0;
    0 1 -1 0 0 0 0;
    0 0 0 1 -1 0 1;
    0 0 1 -1 0 0 0;
    0 0 0 0 0 1 -1;]

ynew = y;

tau = log(1/rand()) / lambda;

ytemp = (y + dy);
ytemp = ytemp.*[ytemp >= 0];

r = rand()*lambda;




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
