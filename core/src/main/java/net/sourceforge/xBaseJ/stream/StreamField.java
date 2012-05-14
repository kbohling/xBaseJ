/**
 * Copyright 2012 Kirby C. Bohling
 * All Rights Reserved
 * 
 * xBaseJ - Java access to dBase files
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
 *
 */
package net.sourceforge.xBaseJ.stream;

import java.io.IOException;
import java.io.InputStream;

public interface StreamField<T> {

    void read(InputStream stream) throws IOException;

    String getName();
    
    T value();
    
    String getString();
    
    char getType();
    
    int getLength();

}
