#!/usr/bin/env python

import math

lines = []

sphere_radius = 2.0

n_strands = 24       	# (const) if the disco ball were a perfect sphere, it would have _n_ rows
n_strands_populated = 16

n_strand_segs = [0, 6, 10, 14, 19, 22, 26, 26, 27, 27, 28, 30, 30, 29, 29, 27, 26 ]

light_width = 0.3	 	# (const) width of each light segment. used to calculate no. of segs in a row.
cur_ring_circum = 0.0

theta_v = 0.0
theta_h = 0.0

cur_x = 0.0
cur_y = 0.0
cur_z = 0.0

for cur_strand in range(1, n_strands_populated + 1) :

	#get the "elevation" angle for the current strand
	theta_v = (math.pi / n_strands) * cur_strand

	# get circumference of this layer (ring)
	cur_ring_circum = 2 * sphere_radius * math.sin(theta_v) * math.pi 

	# how many lights could we pack into this ring?
	n_strand_lights = int(math.floor(cur_ring_circum/light_width))

	theta_start = -1.0 * (float(n_strand_segs[cur_strand])/float(n_strand_lights)) * math.pi

	for cur_light in range(0, n_strand_segs[cur_strand]) :

		# pseudocode snip: start_theta = -1 * (n_segs_populated/n_segs_possible) * pi

		# get the "azimuth" offset of the current light
		theta_offset_h = ((2 * math.pi) / n_strand_lights) * (float(cur_light) + 0.5)

		theta_h = theta_offset_h + theta_start

		cur_x = sphere_radius * math.sin(theta_h) * math.sin(theta_v)
		cur_y = sphere_radius * math.cos(theta_h) * math.sin(theta_v)
		cur_z = sphere_radius * math.cos(theta_v)

		lines.append('  {"point": [%.2f, %.2f, %.2f]}' % (cur_x, cur_y, cur_z))

print '[\n' + ',\n'.join(lines) + '\n]'
