OPC opc;

void setup()
{
  noStroke();
  size(800, 800);
  colorMode(HSB);
  background(50);

  // Connect to the local instance of fcserver
  opc = new OPC(this, "127.0.0.1", 7890);
  opc.showLocations(true);


opc.led(0, 50, 55);
opc.led(1, 53, 53);
opc.led(2, 54, 48);
opc.led(3, 52, 45);
opc.led(4, 47, 45);
opc.led(5, 45, 48);
opc.led(6, 46, 53);
opc.led(7, 50, 57);
opc.led(8, 52, 56);
opc.led(9, 55, 54);
opc.led(10, 56, 52);
opc.led(11, 56, 49);
opc.led(12, 56, 46);
opc.led(13, 54, 44);
opc.led(14, 51, 43);
opc.led(15, 48, 43);
opc.led(16, 45, 44);
opc.led(17, 43, 46);
opc.led(18, 43, 49);
opc.led(19, 43, 52);
opc.led(20, 44, 54);
opc.led(21, 47, 56);
opc.led(22, 50, 59);
opc.led(23, 52, 58);
opc.led(24, 55, 57);
opc.led(25, 57, 55);
opc.led(26, 58, 53);
opc.led(27, 58, 50);
opc.led(28, 58, 47);
opc.led(29, 57, 45);
opc.led(30, 56, 43);
opc.led(31, 53, 41);
opc.led(32, 51, 41);
opc.led(33, 48, 41);
opc.led(34, 46, 41);
opc.led(35, 43, 43);
opc.led(36, 42, 45);
opc.led(37, 41, 47);
opc.led(38, 41, 50);
opc.led(39, 41, 53);
opc.led(40, 42, 55);
opc.led(41, 44, 57);
opc.led(42, 47, 58);
opc.led(43, 50, 61);
opc.led(44, 52, 60);
opc.led(45, 54, 59);
opc.led(46, 57, 58);
opc.led(47, 58, 56);
opc.led(48, 60, 54);
opc.led(49, 60, 51);
opc.led(50, 60, 49);
opc.led(51, 60, 46);
opc.led(52, 59, 44);
opc.led(53, 58, 42);
opc.led(54, 56, 40);
opc.led(55, 53, 39);
opc.led(56, 51, 39);
opc.led(57, 48, 39);
opc.led(58, 46, 39);
opc.led(59, 43, 40);
opc.led(60, 41, 42);
opc.led(61, 40, 44);
opc.led(62, 39, 46);
opc.led(63, 39, 49);
opc.led(64, 39, 51);
opc.led(65, 39, 54);
opc.led(66, 41, 56);
opc.led(67, 42, 58);
opc.led(68, 45, 59);
opc.led(69, 47, 60);
opc.led(70, 50, 63);
opc.led(71, 52, 62);
opc.led(72, 54, 62);
opc.led(73, 57, 60);
opc.led(74, 59, 59);
opc.led(75, 60, 57);
opc.led(76, 62, 54);
opc.led(77, 62, 52);
opc.led(78, 63, 50);
opc.led(79, 62, 47);
opc.led(80, 62, 45);
opc.led(81, 60, 42);
opc.led(82, 59, 40);
opc.led(83, 57, 39);
opc.led(84, 54, 37);
opc.led(85, 52, 37);
opc.led(86, 50, 37);
opc.led(87, 47, 37);
opc.led(88, 45, 37);
opc.led(89, 42, 39);
opc.led(90, 40, 40);
opc.led(91, 39, 42);
opc.led(92, 37, 45);
opc.led(93, 37, 47);
opc.led(94, 37, 50);
opc.led(95, 37, 52);
opc.led(96, 37, 54);
opc.led(97, 39, 57);
opc.led(98, 40, 59);
opc.led(99, 42, 60);
opc.led(100, 45, 62);
opc.led(101, 47, 62);
opc.led(102, 64, 48);
opc.led(103, 64, 51);
opc.led(104, 64, 53);
opc.led(105, 63, 56);
opc.led(106, 62, 58);
opc.led(107, 60, 60);
opc.led(108, 58, 62);
opc.led(109, 56, 63);
opc.led(110, 54, 64);
opc.led(111, 51, 64);
opc.led(112, 49, 64);
opc.led(113, 46, 64);
opc.led(114, 44, 63);
opc.led(115, 41, 62);
opc.led(116, 39, 61);
opc.led(117, 38, 59);
opc.led(118, 36, 56);
opc.led(119, 35, 54);
opc.led(120, 35, 52);
opc.led(121, 35, 49);
opc.led(122, 33, 50);
opc.led(123, 33, 52);
opc.led(124, 33, 55);
opc.led(125, 34, 57);
opc.led(126, 36, 59);
opc.led(127, 37, 61);
opc.led(128, 39, 63);
opc.led(129, 42, 65);
opc.led(130, 44, 66);
opc.led(131, 47, 66);
opc.led(132, 49, 66);
opc.led(133, 52, 66);
opc.led(134, 54, 66);
opc.led(135, 57, 65);
opc.led(136, 59, 64);
opc.led(137, 61, 62);
opc.led(138, 63, 60);
opc.led(139, 64, 58);
opc.led(140, 65, 56);
opc.led(141, 66, 53);
opc.led(142, 66, 50);
opc.led(143, 68, 51);
opc.led(144, 68, 54);
opc.led(145, 67, 56);
opc.led(146, 66, 59);
opc.led(147, 65, 61);
opc.led(148, 63, 63);
opc.led(149, 61, 65);
opc.led(150, 59, 66);
opc.led(151, 56, 67);
opc.led(152, 54, 68);
opc.led(153, 51, 68);
opc.led(154, 48, 68);
opc.led(155, 46, 68);
opc.led(156, 43, 67);
opc.led(157, 41, 66);
opc.led(158, 38, 65);
opc.led(159, 36, 63);
opc.led(160, 35, 61);
opc.led(161, 33, 59);
opc.led(162, 32, 57);
opc.led(163, 31, 54);
opc.led(164, 31, 51);
opc.led(165, 29, 52);
opc.led(166, 29, 54);
opc.led(167, 30, 57);
opc.led(168, 31, 59);
opc.led(169, 33, 62);
opc.led(170, 34, 64);
opc.led(171, 36, 66);
opc.led(172, 39, 67);
opc.led(173, 41, 69);
opc.led(174, 44, 70);
opc.led(175, 46, 70);
opc.led(176, 49, 70);
opc.led(177, 52, 70);
opc.led(178, 55, 70);
opc.led(179, 57, 69);
opc.led(180, 60, 68);
opc.led(181, 62, 66);
opc.led(182, 64, 64);
opc.led(183, 66, 62);
opc.led(184, 68, 60);
opc.led(185, 69, 57);
opc.led(186, 70, 55);
opc.led(187, 70, 52);
opc.led(188, 72, 52);
opc.led(189, 72, 55);
opc.led(190, 71, 57);
opc.led(191, 70, 60);
opc.led(192, 68, 63);
opc.led(193, 67, 65);
opc.led(194, 64, 67);
opc.led(195, 62, 69);
opc.led(196, 60, 70);
opc.led(197, 57, 71);
opc.led(198, 54, 72);
opc.led(199, 51, 72);
opc.led(200, 48, 72);
opc.led(201, 45, 72);
opc.led(202, 43, 71);
opc.led(203, 40, 70);
opc.led(204, 37, 69);
opc.led(205, 35, 67);
opc.led(206, 33, 65);
opc.led(207, 31, 63);
opc.led(208, 29, 61);
opc.led(209, 28, 58);
opc.led(210, 27, 55);
opc.led(211, 27, 52);
opc.led(212, 25, 50);
opc.led(213, 25, 53);
opc.led(214, 25, 56);
opc.led(215, 26, 59);
opc.led(216, 28, 62);
opc.led(217, 29, 64);
opc.led(218, 31, 67);
opc.led(219, 34, 69);
opc.led(220, 36, 71);
opc.led(221, 39, 72);
opc.led(222, 42, 73);
opc.led(223, 45, 74);
opc.led(224, 48, 74);
opc.led(225, 51, 74);
opc.led(226, 54, 74);
opc.led(227, 57, 73);
opc.led(228, 60, 72);
opc.led(229, 63, 71);
opc.led(230, 65, 69);
opc.led(231, 67, 67);
opc.led(232, 69, 65);
opc.led(233, 71, 62);
opc.led(234, 73, 59);
opc.led(235, 74, 56);
opc.led(236, 74, 53);
opc.led(237, 74, 50);
opc.led(238, 76, 44);
opc.led(239, 76, 47);
opc.led(240, 76, 50);
opc.led(241, 76, 54);
opc.led(242, 75, 57);
opc.led(243, 74, 60);
opc.led(244, 73, 63);
opc.led(245, 71, 66);
opc.led(246, 69, 68);
opc.led(247, 66, 71);
opc.led(248, 64, 73);
opc.led(249, 61, 74);
opc.led(250, 58, 75);
opc.led(251, 54, 76);
opc.led(252, 51, 76);
opc.led(253, 48, 76);
opc.led(254, 44, 76);
opc.led(255, 41, 75);
opc.led(256, 38, 74);
opc.led(257, 35, 72);
opc.led(258, 33, 71);
opc.led(259, 30, 68);
opc.led(260, 28, 66);
opc.led(261, 26, 63);
opc.led(262, 25, 60);
opc.led(263, 24, 57);
opc.led(264, 23, 54);
opc.led(265, 23, 50);
opc.led(266, 23, 47);
opc.led(267, 23, 44);
opc.led(268, 26, 32);
opc.led(269, 24, 35);
opc.led(270, 23, 38);
opc.led(271, 22, 42);
opc.led(272, 21, 45);
opc.led(273, 21, 49);
opc.led(274, 21, 52);
opc.led(275, 21, 56);
opc.led(276, 22, 59);
opc.led(277, 24, 63);
opc.led(278, 26, 66);
opc.led(279, 28, 69);
opc.led(280, 30, 71);
opc.led(281, 33, 73);
opc.led(282, 36, 75);
opc.led(283, 39, 77);
opc.led(284, 43, 78);
opc.led(285, 46, 78);
opc.led(286, 50, 78);
opc.led(287, 54, 78);
opc.led(288, 57, 77);
opc.led(289, 61, 76);
opc.led(290, 64, 75);
opc.led(291, 67, 73);
opc.led(292, 70, 70);
opc.led(293, 72, 68);
opc.led(294, 74, 65);
opc.led(295, 76, 62);
opc.led(296, 77, 58);
opc.led(297, 78, 55);
opc.led(298, 78, 51);
opc.led(299, 78, 48);
opc.led(300, 78, 44);
opc.led(301, 77, 41);
opc.led(302, 76, 37);
opc.led(303, 74, 34);
opc.led(304, 72, 31);
opc.led(305, 75, 33);
opc.led(306, 77, 36);
opc.led(307, 79, 40);
opc.led(308, 80, 43);
opc.led(309, 80, 47);
opc.led(310, 80, 51);
opc.led(311, 80, 55);
opc.led(312, 79, 59);
opc.led(313, 78, 63);
opc.led(314, 76, 66);
opc.led(315, 73, 69);
opc.led(316, 71, 72);
opc.led(317, 68, 75);
opc.led(318, 64, 77);
opc.led(319, 61, 78);
opc.led(320, 57, 80);
opc.led(321, 53, 80);
opc.led(322, 49, 80);
opc.led(323, 45, 80);
opc.led(324, 41, 79);
opc.led(325, 38, 78);
opc.led(326, 34, 76);
opc.led(327, 31, 74);
opc.led(328, 28, 72);
opc.led(329, 25, 69);
opc.led(330, 23, 65);
opc.led(331, 21, 62);
opc.led(332, 20, 58);
opc.led(333, 19, 54);
opc.led(334, 19, 50);
opc.led(335, 19, 47);
opc.led(336, 19, 43);
opc.led(337, 20, 39);
opc.led(338, 22, 35);
opc.led(339, 24, 32);
opc.led(340, 19, 36);
opc.led(341, 18, 40);
opc.led(342, 17, 45);
opc.led(343, 17, 49);
opc.led(344, 17, 53);
opc.led(345, 18, 58);
opc.led(346, 19, 62);
opc.led(347, 21, 66);
opc.led(348, 23, 69);
opc.led(349, 26, 73);
opc.led(350, 29, 76);
opc.led(351, 33, 78);
opc.led(352, 37, 80);
opc.led(353, 41, 81);
opc.led(354, 45, 82);
opc.led(355, 50, 82);
opc.led(356, 54, 82);
opc.led(357, 58, 81);
opc.led(358, 62, 80);
opc.led(359, 66, 78);
opc.led(360, 70, 75);
opc.led(361, 73, 72);
opc.led(362, 76, 69);
opc.led(363, 78, 65);
opc.led(364, 80, 61);
opc.led(365, 82, 57);
opc.led(366, 82, 53);
opc.led(367, 82, 49);
opc.led(368, 82, 44);
opc.led(369, 81, 40);
opc.led(370, 80, 36);
opc.led(371, 82, 38);
opc.led(372, 84, 43);
opc.led(373, 84, 47);
opc.led(374, 84, 52);
opc.led(375, 84, 57);
opc.led(376, 82, 62);
opc.led(377, 80, 67);
opc.led(378, 77, 71);
opc.led(379, 74, 74);
opc.led(380, 70, 78);
opc.led(381, 66, 80);
opc.led(382, 62, 82);
opc.led(383, 57, 84);
opc.led(384, 52, 84);
opc.led(385, 47, 84);
opc.led(386, 42, 84);
opc.led(387, 37, 82);
opc.led(388, 33, 80);
opc.led(389, 29, 78);
opc.led(390, 25, 74);
opc.led(391, 22, 71);
opc.led(392, 19, 67);
opc.led(393, 17, 62);
opc.led(394, 15, 57);
opc.led(395, 15, 53);
opc.led(396, 15, 48);
opc.led(397, 15, 43);
opc.led(398, 16, 38);
opc.led(399, 13, 50);
opc.led(400, 13, 56);
opc.led(401, 14, 61);
opc.led(402, 17, 66);
opc.led(403, 20, 71);
opc.led(404, 23, 76);
opc.led(405, 28, 79);
opc.led(406, 32, 82);
opc.led(407, 38, 85);
opc.led(408, 43, 86);
opc.led(409, 49, 86);
opc.led(410, 54, 86);
opc.led(411, 60, 85);
opc.led(412, 65, 83);
opc.led(413, 70, 80);
opc.led(414, 75, 77);
opc.led(415, 79, 72);
opc.led(416, 82, 68);
opc.led(417, 84, 63);
opc.led(418, 86, 57);
opc.led(419, 86, 51);



}

int hue_1 = 0;
float theta_1 = 0;
int hue_2 = 0;
float theta_2 = 0;
int hue_3 = 0;
float theta_3 = 0;


void draw()
{
  background(0);
  
  if (hue_1 > 0)
  {
    hue_1 -= 3;
  }
  else
  {
    hue_1 = 255;
  }
  
  if (theta_1 > 0.05)
  {
    theta_1 -= 0.05;
  }
  else
  {
    theta_1 = TWO_PI;
  }

  
  fill(100, 255, 250, 140);
  
  arc(width/2, height/2, width, height, theta_1, (theta_1 + 0.4) );
  

  if (hue_2 < 252)
  {
    hue_2 += 1;
  }
  else
  {
    hue_2 = 0;
  }
  
  if (theta_2 < TWO_PI)
  {
    theta_2 += 0.1;
  }
  else
  {
    theta_2 = 0;
  }
  
  
  fill(hue_3, 255, 250, 140);
  
  arc(width/2, height/2, width, height, theta_2, (theta_2 + 0.5) );

  if (hue_3 < 255)
  {
    hue_3 += 5;
  }
  else
  {
    hue_3 = 0;
  }
  
  if (theta_3 < TWO_PI)
  {
    theta_3 += 0.02;
  }
  else
  {
    theta_3 = 0;
  }
  
  
  fill(hue_3, 255, 250, 80);
  
  arc(width/2, height/2, width, height, theta_3, (theta_3 + 1) );


  ellipse(mouseX, mouseY, 100, 100);

}

