<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <body>
                <h2>Students</h2>
                <table>
                    <tr>
                        <th>Name</th>
                        <th>birthday</th>
                        <th>education</th>
                    </tr>
                    <xsl:for-each select="studentGroup/students/student">
                        <tr>
                            <td>
                                <xsl:value-of select="name" />
                            </td>
                            <td>
                                <xsl:value-of select="birthday" />
                            </td>
                            <td>
                                <xsl:value-of select="education" />
                            </td>

                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>