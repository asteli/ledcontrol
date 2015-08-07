class GenerateSpheremap {
 
  int cur_x = 0;
  int cur_y = 0;
  
  // constants
  float light_width = 25;
  int n_strand_lights_populated[] = { 6, 10, 14, 19, 22, 26, 26, 27, 27, 28, 30, 30, 29, 29, 27, 26 };
  int n_strands_possible = 24;
  int n_strands_populated = 16;
  float base_ring_step = 20;
  
  // variables we modify in our loops
  int cur_ring_rad;
  int cur_strand_lights_possible;
  int cur_strand_index;
  int cur_strand_light_index;
  int cur_opc_led_index = 0;
  
  float theta_start = 0.0;
  float theta_step = 0.0;
  float theta_h = 0.0;
  
  GenerateSpheremap() {
    // try to get rid of these perhaps
  }
   
   void generate() {
      for ( cur_strand_index = 0; cur_strand_index < n_strands_populated; cur_strand_index++ ) {
        // calculate current ring radius based on ring index, but don't let it be zero.
        cur_ring_rad = int( floor( base_ring_step + (cur_strand_index * base_ring_step) ) );
        
        // divide full circle ( 2PI ) by fractional number of positions possible in ring
        theta_step = 2 * PI /  ( ( cur_ring_rad * 2 * PI ) / light_width );
        
        // theta start = half the lights worth of theta step. times negative one.
        theta_start = -1.0 * theta_step * 0.5 * n_strand_lights_populated[cur_strand_index];
        
        
        // iterate over each of the populated LED position.
        // the python script that generated the simulation layout was indexed starting on 1 apparently.
        for ( cur_strand_light_index = 1; cur_strand_light_index <= (n_strand_lights_populated[cur_strand_index]); cur_strand_light_index++ ) { 
          // get segment theta
          theta_h = (theta_step * cur_strand_light_index) + theta_start; 
          
          cur_x = width/2  + int(floor(cur_ring_rad * sin(theta_h)));
          cur_y = height/2 + int(floor(cur_ring_rad * cos(theta_h)));
          
          // debug
          // println("cur_x:", cur_x, " cur_y:", cur_y, " thetastep:", theta_step, " opc_index:", cur_opc_led_index, " strand_light_index:", cur_strand_light_index);
          
          opc.led(cur_opc_led_index, cur_x, cur_y);
          
          cur_opc_led_index++;
        }  
      } // end generate_spheremap() 
    }
}
