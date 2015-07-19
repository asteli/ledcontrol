#!/usr/bin/env python

import math

lines = []

sphere_radius = 2.0

n_strands = 40       # strands total
n_strand_lights = 30 # lights per strand

theta_v = 0.0
theta_h = 0.0

cur_x = 0.0
cur_y = 0.0
cur_z = 0.0

for cur_strand in range(0, n_strands) :
		#get the "elevation" angle for the current strand
	theta_v = math.pi / n_strands * cur_strand

	for cur_light in range(0, n_strand_lights) :
		theta_h = 2 * math.pi / n_strand_lights * cur_light # get the "azimuth" of the current light


		cur_x = sphere_radius * math.sin(theta_h) * math.sin(theta_v)
		cur_y = sphere_radius * math.cos(theta_h) * math.sin(theta_v)
		cur_z = sphere_radius * math.cos(theta_v)

		#print("\nmade a light\n")
		#print('  {"point": [%.2f, %.2f, %.2f]}' % (cur_x, cur_y, cur_z))



		lines.append('  {"point": [%.2f, %.2f, %.2f]}' % (cur_x, cur_y, cur_z))

print '[\n' + ',\n'.join(lines) + '\n]'

