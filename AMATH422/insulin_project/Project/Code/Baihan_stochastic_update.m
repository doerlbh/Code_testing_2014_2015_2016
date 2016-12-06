
function [ynew, tau, dy] = Baihan_stochastic_update(y, p)

dy = zeros(7,1);
ynew = y;

dy(1) = 0;
dy(2) = p(1)*y(5) - (p(2) + p(3))*y(2);
dy(3) = p(2)*y(2) - p(4)*y(3);
dy(4) = p(4)*y(3) - p(5)*y(4);
dy(5) = p(6)*y(6) + p(7)*y(7) - p(1)*y(5);
dy(6) = p(5)*y(4) - p(6)*y(6);
dy(7) = p(3)*y(2) - p(7)*y(7);

lambda = sum(abs(dy));
tau = log(1/rand()) / lambda;

ytemp = (y + dy);
ytemp = ytemp.*[ytemp >= 0];

r = rand()*lambda;
if r < sum(abs(dy(1)))
    ynew(1) = ytemp(1);
    disp('r 1');
else if r < sum(abs(dy(1:2)));
        ynew(2) = ytemp(2);
            disp('r 2');
    else if r < sum(abs(dy(1:3)));
            ynew(3) = ytemp(3);
                disp('r 3');
        else if r < sum(abs(dy(1:4)));
                ynew(4) = ytemp(4);
                    disp('r 4');
            else if r < sum(abs(dy(1:5)));
                    ynew(5) = ytemp(5);
                        disp('r 5');
                else if r < sum(abs(dy(1:6)));
                        ynew(6) = ytemp(6);
                            disp('r 6');
                    else
                        ynew(7) = ytemp(7);
                            disp('r 7');
                    end
                end
            end
        end
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
