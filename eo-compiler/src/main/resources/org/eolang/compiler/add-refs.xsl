<?xml version="1.0"?>
<!--
The MIT License (MIT)

Copyright (c) 2016-2020 Yegor Bugayenko

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included
in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" id="add-refs" version="2.0">
  <!--
  Here we go through all objects and find what their @base
  are referring to. If we find the object they refer to,
  we add a new @ref attribute to the object. Those objects
  which are not getting @ref attributes after this transformation
  are not visible in the current scope. Maybe they are
  global or just a mistake.
  -->
  <xsl:strip-space elements="*"/>
  <xsl:template match="o[@base]">
    <xsl:variable name="o" select="."/>
    <xsl:copy>
      <xsl:variable name="p" select="ancestor::*[o[@name=$o/@base]][1]"/>
      <xsl:variable name="x" select="$p/o[@name=$o/@base]"/>
      <xsl:if test="$p">
        <xsl:attribute name="ref">
          <xsl:value-of select="$x/@line"/>
        </xsl:attribute>
      </xsl:if>
      <xsl:apply-templates select="node()|@*"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="node()|@*">
    <xsl:copy>
      <xsl:apply-templates select="node()|@*"/>
    </xsl:copy>
  </xsl:template>
</xsl:stylesheet>