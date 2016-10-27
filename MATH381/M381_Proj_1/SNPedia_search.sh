#!/usr/local/bin/
# Baihan Lin
# July 2016

#cat rslist | uniq -d > rslistuniq;

now=$(date +"%Y%m%d");

for j in `cat rslist`; do
	python SNPedia_scrape.py $j;

	for i in *out.txt; do
		grep ">PMID"  $i > ${i//out.txt/PMID};
		rm *out.txt;
		#echo anotherone;
	done
	sed -i '' 's/.*PMID/PMID/g' *PMID*;
	sed -i '' "s#</a>] #$(printf '\t')#g" *PMID*;
	sed -i '' "s#</a><a href=\"/index.php/File:OA-icon.png\" class=\"image\"><img alt=\"OA-icon.png\" src=\"https://media.snpedia.com/images/5/5b/OA-icon.png\" width=\"15\" height=\"15\" />#$(printf '\t')#g" *PMID*;
	sed -i '' "s#</a><a href=\"/index.php/File:OA-icon.png\" class=\"image\"><img alt=\"OA-icon.png\" src=\"https://media.snpedia.com/images/5/5b/OA-icon.png\" width=\"15\" height=\"15\" /> #$(printf '\t')#g" *PMID*;
	sed -i '' "s#</a>]#$(printf '\t')#g" *PMID*;
	sed -i -e "s/^/$j$(printf '\t')/" *PMID*;

done

#for k in *e; do
#	cat $k ${k//-e/} > ${k//-e/.combo};
#	rm $k;
#done

cat Rs* >> PMIDlist$now;
sort PMIDlist$now > PMID$now.sort;
uniq -d PMID$now.sort > PMID$now.uniq;

grep "Rs"  PMID$now.uniq > PMIDRs$now.uniq

#cat *PMID.combo >> PMIDlist$now;
rm Rs*;


