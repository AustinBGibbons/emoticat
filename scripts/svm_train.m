%function svm_trainer(arg1, arg2)
%load arg1;

pidx = randperm(size(train,1));
r_train = train(pidx,:);
labels = r_train(:,1);
features = r_train(:,2:end);

%load arg2;

learnSize = size(learn, 1);

svms = svmtrain(features,labels);
guess = zeros(learnSize,1);
for i=1:learnSize
  guess(i) = svmclassify(svms, learn(i,:));
  if isnan(guess(i))
    guess(i) = 0;
  end
end
disp(sum(guess));
disp(learnSize);
disp(sum(guess) / learnSize);
%exit
