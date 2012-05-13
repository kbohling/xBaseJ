/*
 * xBaseJ - Java access to dBase files
 * Copyright 1997-2011 - American Coders, LTD  - Raleigh NC USA
 * All rights reserved
 * Currently supports only dBase III format DBF, DBT and NDX files
 *                         dBase IV format DBF, DBT, MDX and NDX files
 * American Coders, Ltd
 * P. O. Box 97462
 * Raleigh, NC  27615  USA
 * 1-919-846-2014
 * http://www.americancoders.com
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library Lesser General Public
 * License along with this library; if not, write to the Free
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package net.sourceforge.xBaseJ;

public class DBFConstants {
    public static final byte DBASEIII = 3;
    public static final byte DBASEIV = 4;
    public static final byte DBASEIII_WITH_MEMO = -125;
    public static final byte DBASEIV_WITH_MEMO = -117;
    public static final byte FOXPRO_WITH_MEMO = -11;
    public static final byte NOTDELETED = (byte) ' ';
    public static final byte DELETED = 0x2a;
    public static final char READ_ONLY = 'r';
    public static final String xBaseJVersion = "2.1.R";
}
