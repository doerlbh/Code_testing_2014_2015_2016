function dy = coupleOsci(t,y,p)
 
   dy = zeros(6,1);
   dy(1) = -y(1) + p(1)/(1.+y(3)^p(4)) + p(2);
   dy(2) = -y(2) + p(1)/(1.+y(4)^p(4)) + p(2);
   dy(3) = -p(3)*(y(3)-y(1)) + p(5)*(y(4)-y(3));
   dy(4) = -p(3)*(y(4)-y(2)) + p(5)*(y(3)-y(4));
   
end



