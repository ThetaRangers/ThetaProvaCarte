package it.ferrarally.provacarte;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.AccessibilityDelegateCompat;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class DragExample extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_example);

        new Holder();
    }

    class Holder {
        final MaterialCardView card;
        final MaterialCardView card1;

        Holder() {
            DraggableCoordinatorLayout container = findViewById(R.id.dclContainer);

            card = findViewById(R.id.cdPower);
            card1 = findViewById(R.id.cdPower1);

            container.addDraggableChild(card);
            container.addDraggableChild(card1);

            container.setViewDragListener(
                    new DraggableCoordinatorLayout.ViewDragListener() {
                        @Override
                        public void onViewCaptured(@NonNull View view, int i) {
                            MaterialCardView card = (MaterialCardView) view;
                            card.setDragged(true);

                        }

                        @Override
                        public void onViewReleased(@NonNull View view, float v, float v1) {
                            MaterialCardView card = (MaterialCardView) view;
                            card.setDragged(false);

                        }
                    });

            card.setAccessibilityDelegate(new AccessibilityDelegate());
            card1.setAccessibilityDelegate(new AccessibilityDelegate());

        }

        class AccessibilityDelegate extends View.AccessibilityDelegate{
            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
                // Let the default implementation populate the info.
                super.onInitializeAccessibilityNodeInfo(host, info);
                // Set some other information.
                info.setEnabled(host.isEnabled());

                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) card
                        .getLayoutParams();
                int gravity = layoutParams.gravity;
                boolean isOnLeft = (gravity & Gravity.LEFT) == Gravity.LEFT;
                boolean isOnRight = (gravity & Gravity.RIGHT) == Gravity.RIGHT;
                boolean isOnTop = (gravity & Gravity.TOP) == Gravity.TOP;
                boolean isOnBottom = (gravity & Gravity.BOTTOM) == Gravity.BOTTOM;
                boolean isOnCenter = (gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.CENTER_HORIZONTAL;

                if (!(isOnTop && isOnLeft)) {
                    info.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.move_card_top_left_action,
                            getString(R.string.cat_card_action_move_top_left)));
                }
                if (!(isOnTop && isOnRight)) {
                    info.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.move_card_top_right_action,
                            getString(R.string.cat_card_action_move_top_right)));
                }
                if (!(isOnBottom && isOnLeft)) {
                    info.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.move_card_bottom_left_action,
                            getString(R.string.cat_card_action_move_bottom_left)));
                }
                if (!(isOnBottom && isOnRight)) {
                    info.addAction(new AccessibilityNodeInfo.AccessibilityAction(
                            R.id.move_card_bottom_right_action,
                            getString(R.string.cat_card_action_move_bottom_right)));
                }
                if (!isOnCenter) {
                    info.addAction(new AccessibilityNodeInfo.AccessibilityAction(
                            R.id.move_card_center_action,
                            getString(R.string.cat_card_action_move_center)));
                }
            }
        }

    }
}
