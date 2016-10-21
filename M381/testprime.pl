#!/usr/local/bin/perl

# Baihan Lin, Oct 2016
#
# A perl script to test prime 

$max=12;

for($j=1; $j<$max+1; $j++) {
	for($i=$j+1; $i<$max+1; $i++) {

		# prime number check
		$n=$j*$j+$i*$i+$i*$j+4;
		$d=0; # 0 is prime, 1 is not
		for($c=2;$c<$n;$c++) {
			if($n%$c==0) {
				$d=1;
				break;
			}
		}
		if($d==0){
			print $j, " ", $i, " not prime:", $n, "\n";
		}
	}
}