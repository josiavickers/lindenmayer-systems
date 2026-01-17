#Turtle L-System Drawing#

The turtle is responsible for drawing the L-System trees. There are several parametres that affect how the turtle draws the trees - namely: Turning Angle, Step Length, Stroke Thickness, and Colour. 

Different modulation schemes are seen applied to these parametres in the Turtle class.  

##Turning Angle##
Using Turning Angle as an example, there are three different options to modify angle - affecting the final appearance of the tree. These are:

###1. Turning Angle Spinner###
Adjusting the turning angle spinner directly sets a constant angle by which the turtle moves in both left and right directions. Setting a low turning angle produces a skinnier, sharper tree, whereas a high turning angle yields a wider, more canopy-like tree.

###2. Angle Factor Spinner###
Adjusting the angle factor spinner shifts the turtle's set turning angle by a factor that is increasing with for each new turtle command. This tilts the tree left for positive angle factors and right for negative ones.

###3. Random Number Factor###
The third option is multiplying the turning angle by a random number for each turtle command. The minimum and maximum range of the random number can be specified to adjust the 'chaos' in the tree's appearance. High random number ranges (far from the factor 1)yield more unstable looking trees.

##Parametric L-Systems Workaround##
Whereas the user has options the modulate the tree's appearance by setting factors, these factors are not applied to the tree as a traditional Parametric L-System. For every command in a Parametric L-System implementation, the factor is passed as an additional paramatre in the L-System string. 

In this implementation, the factors are applied post generation of the L-System string. The are computed and applied for each new character processed by the Turtle. 
