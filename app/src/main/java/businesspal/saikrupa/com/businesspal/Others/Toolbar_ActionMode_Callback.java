package businesspal.saikrupa.com.businesspal.Others;

import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import businesspal.saikrupa.com.businesspal.MainActivity;
import businesspal.saikrupa.com.businesspal.R;
import businesspal.saikrupa.com.businesspal.adapters.PayableListAdapter;
import businesspal.saikrupa.com.businesspal.fragments.Payable;
import businesspal.saikrupa.com.businesspal.fragments.Receivable;
import businesspal.saikrupa.com.businesspal.helper.AddPayableHelper;

/**
 * Created by Ndroid on 1/9/2017.
 */

public class Toolbar_ActionMode_Callback implements ActionMode.Callback {

    private Context context;
    private PayableListAdapter recyclerView_adapter;
    private PayableListAdapter listView_adapter;
    private List<AddPayableHelper> message_models;
    private boolean isListViewFragment;


    public Toolbar_ActionMode_Callback(Context context, PayableListAdapter recyclerView_adapter, PayableListAdapter listView_adapter, List<AddPayableHelper> message_models, boolean isListViewFragment) {
        this.context = context;
        this.recyclerView_adapter = recyclerView_adapter;
        this.listView_adapter = listView_adapter;
        this.message_models = message_models;
        this.isListViewFragment = isListViewFragment;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.contextual_action_mode, menu);//Inflate the menu over action mode
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

        //Sometimes the meu will not be visible so for that we need to set their visibility manually in this method
        //So here show action menu according to SDK Levels
        if (Build.VERSION.SDK_INT < 11) {
            MenuItemCompat.setShowAsAction(menu.findItem(R.id.action_delete), MenuItemCompat.SHOW_AS_ACTION_NEVER);
           /* MenuItemCompat.setShowAsAction(menu.findItem(R.id.action_copy), MenuItemCompat.SHOW_AS_ACTION_NEVER);
            MenuItemCompat.setShowAsAction(menu.findItem(R.id.action_forward), MenuItemCompat.SHOW_AS_ACTION_NEVER);*/
        } else {
            menu.findItem(R.id.action_delete).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            /*menu.findItem(R.id.action_copy).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.findItem(R.id.action_forward).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);*/
        }

        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:

                //Check if current action mode is from ListView Fragment or RecyclerView Fragment
                if (isListViewFragment) {
                    Fragment listFragment = new MainActivity().getFragment(1);//Get list view Fragment
                    if (listFragment != null)
                        //If list fragment is not null
                        ((Payable) listFragment).deleteRows();//delete selected rows
                } else {
                    //If current fragment is recycler view fragment
                    Fragment recyclerFragment = new MainActivity().getFragment(2);//Get recycler view fragment
                    if (recyclerFragment != null)
                        //If recycler fragment not null
                        ((Receivable) recyclerFragment).deleteRows();//delete selected rows
                }
                break;
            /*case R.id.action_copy:

                //Get selected ids on basis of current fragment action mode
                SparseBooleanArray selected;
                if (isListViewFragment)
                    selected = listView_adapter
                            .getSelectedIds();
                else
                    selected = recyclerView_adapter
                            .getSelectedIds();

                int selectedMessageSize = selected.size();

                //Loop to all selected items
                for (int i = (selectedMessageSize - 1); i >= 0; i--) {
                    if (selected.valueAt(i)) {
                        //get selected data in Model
                        Item_Model model = message_models.get(selected.keyAt(i));
                        String title = model.getTitle();
                        String subTitle = model.getSubTitle();
                        //Print the data to show if its working properly or not
                        Log.e("Selected Items", "Title - " + title + "n" + "Sub Title - " + subTitle);

                    }
                }
                Toast.makeText(context, "You selected Copy menu.", Toast.LENGTH_SHORT).show();//Show toast
                mode.finish();//Finish action mode
                break;
            case R.id.action_forward:
                Toast.makeText(context, "You selected Forward menu.", Toast.LENGTH_SHORT).show();//Show toast
                mode.finish();//Finish action mode
                break;
*/

        }
        return false;
    }


    @Override
    public void onDestroyActionMode(ActionMode mode) {

        //When action mode destroyed remove selected selections and set action mode to null
        //First check current fragment action mode
        if (isListViewFragment) {
            listView_adapter.removeSelection();  // remove selection
            Fragment listFragment = new MainActivity().getFragment(1);//Get list fragment
            if (listFragment != null)
                ((Payable) listFragment).setNullToActionMode();//Set action mode null
        } else {
            recyclerView_adapter.removeSelection();  // remove selection
            Fragment recyclerFragment = new MainActivity().getFragment(2);//Get recycler fragment
            if (recyclerFragment != null)
                ((Receivable) recyclerFragment).setNullToActionMode();//Set action mode null
        }
    }
}