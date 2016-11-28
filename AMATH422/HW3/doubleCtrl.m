function dy = doubleCtrl(t,y,p)

% y = [y > 1].*y;
dy = zeros(3,1);
dy(1) = 1;
if y(1) > 500 && y(1) < 1000
    z = 1;
else
    z = 0;
end
zx = (z^p(3)) * p(1)/(1.+z^p(3));
zy = (z^p(3))^p(4) * p(5)/(1.+z^p(3));

dy(2) = (y(3)^p(3))^p(4) * p(1)/(1.+y(3)^p(3)) + zx - y(2)*p(6)*[y(2) > 0];
dy(3) = (y(2)^p(3))^p(4) * p(2)/(1.+y(2)^p(3)) + zy - y(3)*p(6)*[y(3) > 0];

end



