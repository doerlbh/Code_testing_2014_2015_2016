%% HW2 for MATH381
% @Author: Baihan Lin
% @Date: Oct 2016

function n = whenComplete(A, n)
% to find out in how many steps can each vertex be connected to another one.
% A is the input adjacency matrix
% n is the starting steps (set to -1 if it is not connected at all)

if n ~= -1
    % only in connected graphs, we find minimum steps towards complete.
    
    if prod(sum(A)) == 0 % if one column of 0, not connected, set n to -1.
        n = -1;
    else
        if prod(prod(A^n)) == 0 % not complete graph yet within n step.
            n = whenComplete(A, n+1);
        end
    end
end

end



