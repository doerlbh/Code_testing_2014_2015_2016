%% HW2 for MATH381
% @Author: Baihan Lin
% @Date: Oct 2016

function d = minD(H, n)
% to minimum distance between every two vertices (min path length).
% H is the input adjacency matrix
% n is the minimum steps to make the graph complete.

fin = [H ~= 0]; % finished connecting pairs
d = 1*[H ~= 0]; % the vertex pairs with distance of 1.
for k = 2:n
    d = d + k.*((H^k ~= 0) - fin); % add
    fin = [H^k ~= 0]; % update the already connected pairs
end

end



