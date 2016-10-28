function yprime=TVO(t,y)
% program for Tattai van Oudenaarden model. Times in seconds
% mRNA half-life is 2 min, protein one 1 hour
k1=0.01;
%b=20;
b=2;
k3=log(2)/120;
k2=b*k3;
k4=log(2)/3600;
yprime=[k1-k3.*y(1);k2.*y(1)-k4.*y(2)];


