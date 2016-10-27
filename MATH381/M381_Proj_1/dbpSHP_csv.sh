#!/usr/local/bin/
# Baihan Lin
# July 2016

for i in *csv; do
	cp $i new-$i
	sed -i '' "s/1.000 /1.000 )/g" new-$i;
	sed -i '' "s/)/)\",\"/g" new-$i;
	sed -i '' "s/\"ss.*-HAPMAP\",//g" new-$i;
	sed -i '' "s/\"ss.*-\",//g" new-$i;
	sed -i '' "s/,\"      ShowHide          \"//g" new-$i;
	sed -i '' "s/\",\"\",\"/\",\"/g" new-$i;
	sed -i '' "s/\"ssID\",\"Submitter\",//g" new-$i;
	sed -i '' "s/,\"Genotype detail\"/,\"\",\"\"/g" new-$i;
	sed -i '' "s/(count)\",\"/(count)\",\"\",\"/g" new-$i;
	sed -i '' "s/\"(/\"1.000 (/g" new-$i;
	sed -i '' "s/1.000 )//g" new-$i;
	python /Users/DoerLBH/Dropbox/git/Geno_SNP_18s_OTU/csv_transpose.py new-$i
done


