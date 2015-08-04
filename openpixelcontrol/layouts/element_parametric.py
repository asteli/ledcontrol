#!/usr/bin/env python

import math

# generates both 3D ring points for gl_server, as well as 2D ring points for the map in Processing
# Running it will spit out both JSON and opc.led lines. You can comment out the relevant print() line
#  or better yet, rewrite the output section to split this data neatly into different textfiles.

def ring(start_index, end_index, phys_theta_v, phys_sphere_radius, phys_led_spacing, map_ring_rad, map_width, map_height, is_clockwise, is_circle, theta_h_offset):

	if (is_circle):
		# step the elements evenly around the circle
		element_theta_step = 2 * math.pi / (end_index - start_index + 1)
		# in complete rings, we start numbering at 0 theta
		cur_start_theta = theta_h_offset
	else:
		# get element spacing in this arc. I did the math on this, and it looks funny, but it's accurate.
		element_theta_step = phys_led_spacing / math.sin(phys_theta_v)
		# calculate arc start theta, with arc centered at 0 radians
		# flip theta to the other side of zero if we're going to be indexing counterclockwise
		if(is_clockwise):
			cur_start_theta = ( -1 * ( ( (end_index - start_index + 1)  * (element_theta_step) * phys_sphere_radius) ) / 2 ) + theta_h_offset
		else:
			cur_start_theta = (  1 * ( ( (end_index - start_index + 1)  * (element_theta_step) * phys_sphere_radius) ) / 2 ) + theta_h_offset

	cur_phys_ring_rad = math.sin(phys_theta_v)

	phys_cur_z = round ( math.cos(phys_theta_v), 4 )

	# start populating the arc
	for cur_element_index in range(start_index, (end_index + 1) ):
			# calculate x and y coordinates of 3D 
			# phys_x, phys_y, phys_z
			
			# get angle of current element
			# flip everything if we're indexing counterclockwise.
			if (is_clockwise):
				cur_element_theta = cur_start_theta + ( element_theta_step * ( cur_element_index - start_index ) )
			else:
				cur_element_theta = cur_start_theta - ( element_theta_step * ( cur_element_index - start_index ) )

			# 3D coordinates based on physical properties. Round to some number of decimal places.
			phys_cur_x = round( (math.sin(cur_element_theta) * cur_phys_ring_rad), 4 )
			phys_cur_y = round( (math.cos(cur_element_theta) * cur_phys_ring_rad), 4 )

			# Get 2D coordinates using same theta, but a separate radius.
			map_cur_x = int( (math.sin(cur_element_theta) * map_ring_rad) + (map_width  / 2) )
			map_cur_y = int( (math.cos(cur_element_theta) * map_ring_rad) + (map_height / 2) )

			# Print gl_server JSON directive
			print( "  {\"point\": [" + str(phys_cur_x) + ", " + str(phys_cur_y) + ", " + str(phys_cur_z) +"]},")

			# print Processing OPC layout directive
			#print( "opc.led(" + str(cur_element_index) + ", " + str(map_cur_x) + ", " + str(map_cur_y) + ");" )


# Main Begin


# here's an example. 
ring(start_index = 0, end_index = 6, phys_theta_v = 0.1587, phys_sphere_radius = 0.9652, phys_led_spacing = 0.123, map_ring_rad = 50, map_width = 800, map_height = 800, is_clockwise = 1, is_circle = 1, theta_h_offset = 0)
ring(start_index = 7, end_index = 21, phys_theta_v = 0.2870, phys_sphere_radius = 0.9652, phys_led_spacing = 0.123, map_ring_rad = 70, map_width = 800, map_height = 800, is_clockwise = 1, is_circle = 1, theta_h_offset = 0)
ring(start_index = 22, end_index = 42, phys_theta_v = 0.4153, phys_sphere_radius = 0.9652, phys_led_spacing = 0.123, map_ring_rad = 90, map_width = 800, map_height = 800, is_clockwise = 1, is_circle = 1, theta_h_offset = 0)
ring(start_index = 43, end_index = 69, phys_theta_v = 0.5436, phys_sphere_radius = 0.9652, phys_led_spacing = 0.123, map_ring_rad = 110, map_width = 800, map_height = 800, is_clockwise = 0, is_circle = 0, theta_h_offset = 0)
ring(start_index = 70, end_index = 101, phys_theta_v = 0.6719, phys_sphere_radius = 0.9652, phys_led_spacing = 0.123, map_ring_rad = 130, map_width = 800, map_height = 800, is_clockwise = 1, is_circle = 0, theta_h_offset = 0)
ring(start_index = 102, end_index = 121, phys_theta_v = 0.8002, phys_sphere_radius = 0.9652, phys_led_spacing = 0.123, map_ring_rad = 150, map_width = 800, map_height = 800, is_clockwise = 0, is_circle = 0, theta_h_offset = 0)
ring(start_index = 122, end_index = 142, phys_theta_v = 0.9285, phys_sphere_radius = 0.9652, phys_led_spacing = 0.123, map_ring_rad = 170, map_width = 800, map_height = 800, is_clockwise = 1, is_circle = 0, theta_h_offset = 0)
ring(start_index = 143, end_index = 164, phys_theta_v = 1.0568, phys_sphere_radius = 0.9652, phys_led_spacing = 0.123, map_ring_rad = 190, map_width = 800, map_height = 800, is_clockwise = 0, is_circle = 0, theta_h_offset = 0)
ring(start_index = 165, end_index = 187, phys_theta_v = 1.1851, phys_sphere_radius = 0.9652, phys_led_spacing = 0.123, map_ring_rad = 210, map_width = 800, map_height = 800, is_clockwise = 1, is_circle = 0, theta_h_offset = 0)
ring(start_index = 188, end_index = 211, phys_theta_v = 1.3134, phys_sphere_radius = 0.9652, phys_led_spacing = 0.123, map_ring_rad = 230, map_width = 800, map_height = 800, is_clockwise = 0, is_circle = 0, theta_h_offset = 0)
ring(start_index = 212, end_index = 237, phys_theta_v = 1.4417, phys_sphere_radius = 0.9652, phys_led_spacing = 0.123, map_ring_rad = 250, map_width = 800, map_height = 800, is_clockwise = 1, is_circle = 0, theta_h_offset = 0)



#ring(start_index = 0, end_index = 24, phys_theta_v = 1.4417, phys_sphere_radius = 0.9652, phys_led_spacing = 0.123, map_ring_rad = 250, map_width = 800, map_height = 800, is_clockwise = 0, is_circle = 0, theta_h_offset = 0)
#ring(start_index = 25, end_index = 48, phys_theta_v = 1.3134, phys_sphere_radius = 0.9652, phys_led_spacing = 0.123, map_ring_rad = 230, map_width = 800, map_height = 800, is_clockwise = 1, is_circle = 0, theta_h_offset = 0)
#ring(start_index = 49, end_index = 70, phys_theta_v = 1.1851, phys_sphere_radius = 0.9652, phys_led_spacing = 0.123, map_ring_rad = 210, map_width = 800, map_height = 800, is_clockwise = 1, is_circle = 0, theta_h_offset = 0)
#ring(start_index = 71, end_index = 91, phys_theta_v = 1.0568, phys_sphere_radius = 0.9652, phys_led_spacing = 0.123, map_ring_rad = 190, map_width = 800, map_height = 800, is_clockwise = 0, is_circle = 0, theta_h_offset = 0)

#ring(start_index = 92, end_index = 92, phys_theta_v = 0.9285, phys_sphere_radius = 0.9652, phys_led_spacing = 0.123, map_ring_rad = 170, map_width = 800, map_height = 800, is_clockwise = 1, is_circle = 0, theta_h_offset = 0)
#ring(start_index = 138, end_index = 158, phys_theta_v = 1.0568, phys_sphere_radius = 0.9652, phys_led_spacing = 0.123, map_ring_rad = 190, map_width = 800, map_height = 800, is_clockwise = 0, is_circle = 0, theta_h_offset = 0)
