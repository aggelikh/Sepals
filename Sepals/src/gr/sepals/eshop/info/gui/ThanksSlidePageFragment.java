/**This file is part of Sepals.
 *
 * Sepals is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * Sepals is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Sepals.  If not, see <http://www.gnu.org/licenses/>.
 */
package gr.sepals.eshop.info.gui;

import gr.sepals.eshop.R;
import gr.sepals.eshop.persistence.InternalDatabaseKeys;
import gr.sepals.eshop.util.db.Settings;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class ThanksSlidePageFragment extends Fragment implements
        CompoundButton.OnCheckedChangeListener {

    private TextView title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_info_thanks, container, false);
        
        title = (TextView) rootView.findViewById(R.id.info_thanks_title_textview);
        Typeface titleFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/GFS_Pyrsos.otf");
        title.setTypeface(titleFont, Typeface.BOLD);

        CheckBox hideInfo = (CheckBox) rootView.findViewById(R.id.hide_info_checkBox);
        Typeface descFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/GFS_Goschen-Italic.otf");
        hideInfo.setTypeface(descFont);
        hideInfo.setOnCheckedChangeListener(this);

        // update the fragment content
        hideInfo.setChecked(Settings.getBoolean(getActivity(),
                InternalDatabaseKeys.HIDE_INTRO_INFO, false));
        return rootView;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Settings.setBoolean(getActivity(),
                InternalDatabaseKeys.HIDE_INTRO_INFO, isChecked);
    }
}
