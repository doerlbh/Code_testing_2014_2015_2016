
#!/usr/local/bin/perl

#
# Baihan Lin, Oct 2016
#
# A perl script to generate an lpsolve-format IP (integer program) 

$boardSizeX=4;
$boardSizeY=4;

## print out objective function
print "min:";
for ($j=0; $j<$boardSizeY; $j++) {
	for($i=0; $i<$boardSizeX; $i++) {
		print "+x_",$j,"_",$i;
	}
}
print ";\n";
		
## generate a constraint for each square on the board that
## ensures that the square is attacked

for($j=0; $j<$boardSizeY; $j++) {
	for($i=0; $i<$boardSizeX; $i++) {
		
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
for($j=0; $j<$boardSizeY; $j++) {
	for($i=0; $i<$boardSizeX; $i++) {
	if ($i+$j>0) { print ","; } ## prepend a comma after the first variable
		print "x_",$j,"_",$i;
	}
}
print ";\n";