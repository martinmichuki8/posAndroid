/*
 * 	   Created by Daniel Nadeau
 * 	   daniel.nadeau01@gmail.com
 * 	   danielnadeau.blogspot.com
 * 
 * 	   Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package com.echo.holographlibrarysample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.echo.holographlibrary.MultiSeriesDonutGraph;
import com.echo.holographlibrary.MultiSeriesDonutSlice;

public class MultiSeriesDonutFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View v = inflater.inflate(R.layout.fragment_multiseriesdonutgraph, container, false);
        MultiSeriesDonutGraph mg = (MultiSeriesDonutGraph) v.findViewById(R.id.multiseriesdonutgraph);

        MultiSeriesDonutSlice slice = new MultiSeriesDonutSlice();
        slice.setColor(Color.parseColor("#99CC00"));
        slice.setValue(2);
        mg.addSlice(0, slice);
        slice = new MultiSeriesDonutSlice();
        slice.setColor(Color.parseColor("#FFBB33"));
        slice.setValue(3);
        mg.addSlice(0, slice);
        slice = new MultiSeriesDonutSlice();
        slice.setColor(Color.parseColor("#AA66CC"));
        slice.setValue(8);
        mg.addSlice(0, slice);

        slice = new MultiSeriesDonutSlice();
        slice.setColor(Color.parseColor("#99CC00"));
        slice.setValue(8);
        mg.addSlice(1, slice);
        slice = new MultiSeriesDonutSlice();
        slice.setColor(Color.parseColor("#FFBB33"));
        slice.setValue(5);
        mg.addSlice(1, slice);
        slice = new MultiSeriesDonutSlice();
        slice.setColor(Color.parseColor("#AA66CC"));
        slice.setValue(3);
        mg.addSlice(1, slice);

        mg.setOnSliceClickedListener(new MultiSeriesDonutGraph.OnSeriesSliceClickedListener()
        {
            @Override
            public void onClick(int series, int index) {}
        });

        return v;
    }
}
