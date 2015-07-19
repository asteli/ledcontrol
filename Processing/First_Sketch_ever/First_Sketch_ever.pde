int linecolor_r = 0;
int linecolor_g = 0;
int linecolor_b = 0;

int drawcounter = 0;

PFont font_ar16;

void setup()
{
  size(1024, 768, P3D);
  background(0);
  font_ar16 = createFont("Arial", 24, true);
}



void draw()
{
   drawcounter ++;
   
   if (mousePressed)
   {
     background(0);
   }
   else
   {
     if(linecolor_r < 255)
     {
       linecolor_r += 1;
     }
     else
     {
       linecolor_r = 0;
     }

     if(linecolor_g < 255)
     {
       linecolor_g += 2;
     }
     else
     {
       linecolor_g = 0;
     }     
     
     if(linecolor_b < 255)
     {
       linecolor_b += 3;
     }
     else
     {
       linecolor_b = 0;
     }     
     
     
     fill(0, 255, 255);
     strokeWeight(4);
     stroke(linecolor_r, linecolor_g, linecolor_b);
     line(mouseX, mouseY, width, height);
   }
   
   textFont(font_ar16);
   fill(0, 255, 0);
   text(drawcounter, mouseX - 30, mouseY - 30);
   
}
