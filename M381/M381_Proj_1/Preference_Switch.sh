#!/usr/local/bin/
# Baihan Lin
# October 2016

#cat rslist | uniq -d > rslistuniq;

rm pref.run;
rm pref_ind_switch.sh;
rm pref_ind_switch_final.sh;
rm new_pref.out;

cp pref.list pref1.run;
sed -i -e 's/^/if [ $j = /' pref1.run;

cp prefMF.list pref2.run;
sed -i -e 's/^/ ];then echo /' pref2.run;

paste pref1.run pref2.run > pref_ind_switch.sh;
sed -i '' 's#$# >> new_pref.out;fi#' pref_ind_switch.sh;

#sed -i -e "s/$(print '\t')/ /" pref_ind_switch.sh;

cat pref_ind_switch.sh | tr -d '\011]' > pref_ind_switch_final.sh;
#cat pref_ind_switch.sh > pref_ind_switch_final.sh;

for j in `cat FoodGroup.list`; do
	sh pref_ind_switch_final.sh
done

