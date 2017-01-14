% RNA interference modeled by Stochastic Monte Carlos
% Author: Baihan Lin
% Date: Nov 2016

function [ynew, tau] = stoch_update(y, p)

gm1 = p(1);
gm2 = p(2);
gm3 = p(3);
K1 = p(4);
K2 = p(5);
K3 = p(6);
H = p(7);

rates = [K1;
    K2;
    K3;
    H;
    gm1*y(2);
    gm1*y(1);
    gm2*y(3);
    gm3*y(4)];

lambda = sum(abs(rates));

transition = [0 1 0 -1 0 -1 0 0;
    1 0 0 -1 -1 0 0 0;
    0 0 0 1 0 0 -1 0;
    0 0 1 0 0 0 0 -1];

ynew = y;

tau = log(1/rand()) / lambda;

r = rand()*lambda;

current = 0;
selection = 1;

for it = 1:length(rates)
    current = current + rates(it);
    if current > r
        selection = it;
        disp(selection);
        break;
    end
end

ynew
ynew = ynew + transition(:,selection);
ynew = ynew.*[ynew >= 0];

end