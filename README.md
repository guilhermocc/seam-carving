# Simple Search Engine
This is the repo for my [Seam Carving](https://hyperskill.org/projects/89?goal=347) JetBrains Academy Project

# About
Seam carving is an image processing technique for content aware image resizing.
Content aware means that it saves objects and object aspect ratio at processed image.

# Learning outcomes
You will learn how to work with images. At the same time you will get familiar with basic graph processing algorithms. Implement seam carving technique yourself and try it on any picture you like.

# Final stage version

## Description
Now you have everything to resize the image while preserving its content. Simply remove the seam, then remove the seam from the resulting image and so on!

## Objective
Add two more command line parameters. Use parameter -width for the number of vertical seams to remove and -height for horizontal seams.

At this stage, your program should reduce the input image and save the result.

Remove the specified number of vertical seams first, and then remove the horizontal seams.

In this stage, you should download this archive and access the images via the absolute path to the folder where you saved these images. All the tests in this and in the following stages use this set of images. But despite the fact you reading images by an absolute path, you should save newly created images via relative path, in the current directory.

## Example
The greater-than symbol followed by a space (> ) represents the user input. Note that it's not part of the input.

### Example 1:

In the following example, the program should reduce the image width by 125 pixels and height by 50 pixels

> java Main -in sky.png -out sky-reduced.png -width 125 -height 50
For the following sky.png:

![alt text](https://lh6.googleusercontent.com/3UunASdpPsuGEyiTcm_hpcRkO-HXWiT0AEOYffQPtrtFPgW8C_W4pcCsEVITifZOd0R1alttczfaeeRAVdn8XSL3ZcT1XJ5BPUnoPoR8zgeo-43Pf8cgxWwvjb1gZSm3pmQduG0n)

sky-reduced.png should look like this:

![alt text](https://lh3.googleusercontent.com/_Ym63YhQvvucO8qUSYkpxas2nKdTGTLcRUH_lc7t91f8x7kLwoKk9KX_kbhvKhi3sxQiRadV59evKPcDkehx8rEKM86eSaPP9uhafmliHco6b7TZVGxp_L7ac49H8h2Uc_WL4VRy)


Example 2:

> java Main -in trees.png -out trees-reduced.png -width 100 -height 30
For trees.png:


![alt text](https://lh3.googleusercontent.com/aNYkWYZUYMLihMdtI341OoiBqUOENSBtjm30v950oHtMBm01e_eNo4grjNPgp_MgbqnVu0wX9UVzab47O65h0Dr8HfZv98eHCeGpr5byUcES8c_-gO33feDUfB6R--HndOdcMxrP)

trees-reduced.png should look like this:

![alt text](https://lh6.googleusercontent.com/jjA12yuFxhRWVebJu67Nrqdru6iNi77ak3_UjP6C54LPatfnjYKL1FplB8D2G0WpZhqK6qIRgA65gbOEQFigu99Nx1H-SvhVWhOscUJvlpYDN4LZynRXn__nR5131iIRZFWr8pRX)

# Evolution
I will be elaborated soon as i finish the final version
