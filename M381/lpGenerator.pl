
#!/usr/local/bin/perl

#
# Baihan Lin, Oct 2016
#
# A perl script to generate an lpsolve-format IP (integer program) 

$max=12;

## print out objective function
print "min:";
	for($i=0; $i<$max; $i++) {
		print "+y_",$i;
	}
print ";\n";

## constraint 1
for($j=0; $j<$max; $j++) {
	for($i=0; $i<$max; $i++) {
		print "+x_",$j,"_",$i;
	}
	print "=1;\n"
}

## constraint 2
for($j=0; $j<$max; $j++) {
	for($i=0; $i<$max; $i++) {
		print "x_",$j,"_",$i," -", "y_",$i,"<=0;\n";
	}
}

## constraint 3
for($j=0; $j<$boardSizeY; $j++) {
	for($i=0; $i<$boardSizeX; $i++) {

		for($c=2;$c<=$n-1;$c++) {
			if($n%$c==0)




		
		## find all the locations from which (i,j) could be attacked;
		## add each one to the constraint for (i,j): (i,j) must be attacked!
		
		for($k=0; $k<8; ++$k) { # eight possible square could attack (i,j)
			$xChangeAbs=2-(int($k/2) % 2);
			if ($k<4) { $xDelta=$xChangeAbs; }
				else {$xDelta = -$xChangeAbs; }
				
			$yChangeAbs=3-$xChangeAbs;
			if (($k % 2)==0) { $yDelta = $yChangeAbs; }
				else { $yDelta = -$yChangeAbs; }
					
			$targetX=$i+$xDelta;
			$targetY=$j+$yDelta;
			
			if (($targetX>=0) && ($targetX<$boardSizeX) && ($targetY>=0) && ($targetY<$boardSizeY)) {
				
				## target is an actual square on the board, so add this location to the constraint
				
				print "+x_",$targetX,"_",$targetY;
			}
			
		}
		
		## finish the constraint
		
		print ">=1;\n";
		
 	}
 }


## specify that all variables are binary
print "bin ";
for($i=0; $i<$max; $i++) {
	if ($i>0) { print ","; } 
	print "+y_",$i;
}
for($j=0; $j<$max; $j++) {
	for($i=0; $i<$max; $i++) {
	if ($i+$j>0) { print ","; } ## prepend a comma after the first variable
		print "x_",$j,"_",$i;
	}
}
print ";\n";