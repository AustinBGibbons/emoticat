function [features labels] = load_mat(arg1)
input = load(arg1, '-ascii');
pidx = randperm(size(input,1));
r_input = input(pidx,:);
labels = r_input(:,1);
features = r_input(:,2:end);
