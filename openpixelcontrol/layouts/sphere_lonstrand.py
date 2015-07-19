#!/usr/bin/env python

import math

lines = []

sphere_radius = 2.0

n_strands = 40       # strands total
n_strand_lights = 15 # lights per strand

theta_v = 0.0
theta_h = 0.0

cur_x = 0.0
cur_y = 0.0
cur_z = 0.0

for cur_strand in range(0, n_strands) :

	theta_h = 2 * math.pi / n_strands * cur_strand # get the "azimuth" of the current strand
		
	#print("\nmade a strand\n  theta_h = " + str(theta_h) + "\n")

	for cur_light in range(0, n_strand_lights) :
		#get the "elevation" angle for the current light
		theta_v = math.pi / n_strand_lights * cur_light

		cur_x = sphere_radius * math.sin(theta_h) * math.sin(theta_v)
		cur_y = sphere_radius * math.cos(theta_h) * math.sin(theta_v)
		cur_z = sphere_radius * math.cos(theta_v)

		lines.append('  {"point": [%.2f, %.2f, %.2f]}' % (cur_x, cur_y, cur_z))

print '[\n' + ',\n'.join(lines) + '\n]'

