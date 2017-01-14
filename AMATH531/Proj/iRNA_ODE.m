% RNA interference modeled by Deterministic ODE
% Author: Baihan Lin
% Date: Nov 2016

%  dvdt = ep1 * (1 - v - H1 * v * r);
%  drdt = ep1 * (1 - r - H2 * v * r);
%  dqdt = ep2 * (- q + H3 * v * r);
%  dsdt = r - s;

function dy = iRNA_ODE(t,y,p)

ep1 = p(1);
ep2 = p(2);
H1 = p(3);
H2 = p(4);
H3 = p(5);

dy = zeros(4,1);

dy(1) = ep1 * (1 - y(1) - H1 * y(1) * y(2));
dy(2) = ep1 * (1 - y(2) - H2 * y(1) * y(2));
dy(3) = ep2 * (- y(3) + H3 * y(1) * y(2));
dy(4) = y(2) - y(4);

end